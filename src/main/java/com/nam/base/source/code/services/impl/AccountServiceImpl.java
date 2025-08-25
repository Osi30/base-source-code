package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.dtos.dto.AccountIdentity;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.enums.AccountIdentifier;
import com.nam.base.source.code.enums.AccountStatus;
import com.nam.base.source.code.exceptions.exceptions.AccountException;
import com.nam.base.source.code.exceptions.exceptions.AuthException;
import com.nam.base.source.code.mappers.AccountMapper;
import com.nam.base.source.code.repositories.AccountRepo;
import com.nam.base.source.code.services.AccountService;
import com.nam.base.source.code.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {
    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;

    @Override
    public Account register(AuthRequest authRequest) {
        AccountIdentity accountIdentity = getAccountByIdentity(authRequest);
        Account existedAccount = accountIdentity.getAccount();

        // Throw exception for active/banned/deleted account
        if (existedAccount != null && !accountIdentity.getIsInactive()) {
            throw new AuthException("Account already exists with: " + accountIdentity.getIdentity()
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
                .orElseThrow(() -> new AccountException(accountId));
    }

    private AccountIdentity getAccountByIdentity(AuthRequest authRequest) {
        Account account = null;
        AccountIdentifier accountIdentifier = null;
        boolean isInactiveAccount = false;

        if (!ValidationUtils.isNullOrEmpty(authRequest.getEmail())) {
            account = accountRepo.findByEmail(authRequest.getEmail());
            accountIdentifier = AccountIdentifier.EMAIL;
        }

        if (!ValidationUtils.isNullOrEmpty(authRequest.getUsername())
                && account == null) {
            account = accountRepo.findByUsername(authRequest.getUsername());
            accountIdentifier = AccountIdentifier.USERNAME;
        }

        if (!ValidationUtils.isNullOrEmpty(authRequest.getPhoneNumber())
                && account == null) {
            account = accountRepo.findByPhoneNumber(authRequest.getPhoneNumber());
            accountIdentifier = AccountIdentifier.PHONE;
        }

        // Condition for inactive account
        if (account != null) {
            isInactiveAccount = account.getStatus().equals(AccountStatus.INACTIVE);
        }

        return AccountIdentity.builder()
                .account(account)
                .isInactive(isInactiveAccount)
                .identity(accountIdentifier)
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        Account account = accountRepo.findByIdentifier(identifier);
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
}
