package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.dtos.dto.AccountIdentity;
import com.nam.base.source.code.dtos.request.AccountRequest;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.dtos.response.AccountResponse;
import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.enums.AccountIdentifier;
import com.nam.base.source.code.enums.AccountStatus;
import com.nam.base.source.code.exceptions.exceptions.AccountException;
import com.nam.base.source.code.exceptions.exceptions.AuthException;
import com.nam.base.source.code.mappers.AccountMapper;
import com.nam.base.source.code.repositories.AccountRepo;
import com.nam.base.source.code.services.AccountService;
import com.nam.base.source.code.services.VerifyTokenService;
import com.nam.base.source.code.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {
    private final VerifyTokenService verifyTokenService;
    private final AccountRepo accountRepo;
    private final ModelMapper modelMapper;
    private final AccountMapper accountMapper;

    @Override
    public Account createAccount(AuthRequest authRequest) {
        // Validation
        AccountIdentity accountIdentity = validateRequestedAuth(authRequest);
        Account existedAccount = accountIdentity.getProcessAccount();

        // Throw exception for active/banned/deleted account
        if (existedAccount != null && !accountIdentity.getIsInactive()) {
            throw new AuthException("Account already exists with: "
                    + accountIdentity.getIdentifier().getValue()
                    + ", status: " + existedAccount.getStatus().getName());
        }

        // Update new info to inactive account
        if (existedAccount != null) {
            existedAccount = accountMapper.updateAccount(authRequest, existedAccount);
            return accountRepo.save(existedAccount);
        }

        // Create new account
        Account account = accountMapper.toAccount(authRequest);
        return accountRepo.save(account);
    }

    @Override
    public Account getAccountById(String accountId) {
        return accountRepo.findById(accountId)
                .orElseThrow(() -> new AccountException("Account not found with id: " + accountId));
    }

    @Override
    public Account getAccountByIdentifier(String identifier) {
        return accountRepo.findByIdentifier(identifier);
    }

    @Override
    public AccountResponse getAccountResponseById(String accountId) {
        return accountMapper.toAccountResponse(getAccountById(accountId));
    }

    @Override
    public AccountResponse updateAccount(AccountRequest request, String accountId) {
        Account existingAccount = getAccountById(accountId);

        // Validation
        validateUpdatedAuth(request, existingAccount);

        // Map Common Info
        existingAccount = accountMapper.updateAccount(request, existingAccount);

        // Email Verification Step
        if (existingAccount.getEmail() != null && request.getEmail() != null
                && !existingAccount.getEmail().equals(request.getEmail())) {
            existingAccount.setEmail(request.getEmail());
            verifyTokenService.verifyEmail(existingAccount);
        }


        return accountMapper.toAccountResponse(accountRepo.save(existingAccount));
    }

    @Override
    public String deleteAccount(String accountId) {
        Account account = getAccountById(accountId);

        // Set status
        account.setStatus(AccountStatus.DELETED);

        // Set unique fields
        String prefix = "deleted-" + UUID.randomUUID() + "-";
        account.setEmail(prefix + account.getEmail());
        account.setPhoneNumber(prefix + account.getPhoneNumber());
        account.setUsername(prefix + account.getUsername());

        accountRepo.save(account);

        return "Delete Account Successfully";
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepo.findAllByStatusIsIn(List.of(AccountStatus.ACTIVE, AccountStatus.BANNED));
    }

    @Override
    public List<AccountResponse> getAllAccountResponses() {
        return getAllAccounts().stream()
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Account account = getAccountByIdentifier(identifier);
        if (account == null) {
            throw new UsernameNotFoundException("Account not found with: " + identifier);
        }

        if (!account.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new AccountException("Account is: " + account.getStatus().getName());
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        // For multi roles

        return new User(account.getId(), account.getPassword(), authorities);
    }

    /**
     * Retrieves an account identity based on the provided authentication request.
     * <p>
     * This method finds account by email, username, mobile phone number,
     *
     * @param authRequest The request containing the user's identity (email or mobile number).
     * @return The AccountIdentity associated with the user, or null if no matching account is found.
     */
    private AccountIdentity validateRequestedAuth(AuthRequest authRequest) {
        // Validation
        AccountIdentity identity = new AccountIdentity();

        // Email
        identity.setIdentity(authRequest.getEmail());
        validateIdentity(identity, AccountIdentifier.EMAIL);


        // Username
        identity.setIdentity(authRequest.getUsername());
        validateIdentity(identity, AccountIdentifier.USERNAME);

        // Phone
        identity.setIdentity(authRequest.getPhoneNumber());
        validateIdentity(identity, AccountIdentifier.PHONE);

        return identity;
    }

    private void validateIdentity(AccountIdentity identity, AccountIdentifier identifier) {
        String identityValue = identity.getIdentity();

        if (ValidationUtils.isNullOrEmpty(identityValue)) {
            return;
        }

        Account account = switch (identifier) {
            case EMAIL -> accountRepo.findByEmail(identityValue);
            case PHONE -> accountRepo.findByPhoneNumber(identityValue);
            default -> accountRepo.findByUsername(identityValue);
        };

        // No new account found
        if (account == null) {
            return;
        }

        // No found account before
        if (identity.getProcessAccount() == null) {
            identity.setProcessAccount(account);
            identity.setIdentifier(identifier);
            identity.setIsInactive(account.getStatus().equals(AccountStatus.INACTIVE));
            return;
        }

        // Throw Error If Exist Two Different Account
        if (!account.getId().equals(identity.getProcessAccount().getId())) {
            throw new AccountException("Already exist identity: " + identifier.getValue());
        }
    }

    private void validateUpdatedAuth(AccountRequest accountRequest, Account existingAccount) {
        // A. Remove Duplication Request
        // 1. Email
        if (existingAccount.getEmail() != null && accountRequest.getEmail() != null
                && existingAccount.getEmail().equals(accountRequest.getEmail())) {
            accountRequest.setEmail(null);
        }


        // 2. Phone
        if (existingAccount.getPhoneNumber() != null && accountRequest.getPhoneNumber() != null
                && existingAccount.getPhoneNumber().equals(accountRequest.getPhoneNumber())) {
            accountRequest.setPhoneNumber(null);
        }


        // 3. Username
        if (existingAccount.getUsername() != null && accountRequest.getUsername() != null
                && existingAccount.getUsername().equals(accountRequest.getUsername())) {
            accountRequest.setUsername(null);
        }

        // B. Find Existing Account
        AccountIdentity accountIdentity = validateRequestedAuth(modelMapper.map(accountRequest, AuthRequest.class));
        if (accountIdentity.getProcessAccount() != null) {
            throw new AccountException("Account already exists with: " + accountIdentity.getIdentifier().getValue());
        }
    }
}
