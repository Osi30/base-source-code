package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.dtos.dto.AccountIdentity;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.enums.AccountIdentifier;
import com.nam.base.source.code.exceptions.exceptions.AuthException;
import com.nam.base.source.code.mappers.AccountMapper;
import com.nam.base.source.code.repositories.AccountRepo;
import com.nam.base.source.code.services.AccountService;
import com.nam.base.source.code.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;

    @Override
    public Account register(AuthRequest authRequest) {
        AccountIdentity accountIdentity = getAccountByIdentity(authRequest);

        if (accountIdentity.getAccount() != null) {
            throw new AuthException("Account already exists with: " + accountIdentity.getIdentity());
        }

        Account account = accountMapper.toAccount(authRequest);
        return accountRepo.save(account);
    }

    private AccountIdentity getAccountByIdentity(AuthRequest authRequest) {
        Account account = null;
        AccountIdentifier accountIdentifier = null;

        if (!ValidationUtils.isNullOrEmpty(authRequest.getEmail())) {
            account = accountRepo.findByEmail(authRequest.getEmail());
            accountIdentifier = AccountIdentifier.EMAIL;
        }

        if (!ValidationUtils.isNullOrEmpty(authRequest.getUsername())) {
            account = accountRepo.findByUsername(authRequest.getUsername());
            accountIdentifier = AccountIdentifier.USERNAME;
        }

        if (!ValidationUtils.isNullOrEmpty(authRequest.getPhoneNumber())) {
            account = accountRepo.findByPhoneNumber(authRequest.getPhoneNumber());
            accountIdentifier = AccountIdentifier.PHONE;
        }

        if (!ValidationUtils.isNullOrEmpty(authRequest.getIdentifier())) {
            account = accountRepo.findByIdentifier(authRequest.getIdentifier());
        }

        return AccountIdentity.builder()
                .account(account)
                .identity(accountIdentifier)
                .build();
    }
}
