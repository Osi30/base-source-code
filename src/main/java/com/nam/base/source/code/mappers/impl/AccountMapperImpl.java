package com.nam.base.source.code.mappers.impl;

import com.nam.base.source.code.dtos.request.AccountRequest;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.dtos.response.AccountResponse;
import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.enums.AccountStatus;
import com.nam.base.source.code.exceptions.exceptions.AuthException;
import com.nam.base.source.code.mappers.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Account toAccount(AuthRequest authRequest) {
        Account account = modelMapper.map(authRequest, Account.class);

        if (authRequest.getPassword() == null) {
            throw new AuthException("Password is required");
        }
        account.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        account.setStatus(authRequest.getEmail() == null ? AccountStatus.ACTIVE : AccountStatus.INACTIVE);

//        account.setAccountRole(authRequest.getAccountRoleId() == null
//        ? AccountRole.CUSTOMER : authRequest.getAccountRoleId());

        return account;
    }

    @Override
    public Account updateAccount(AuthRequest request, Account existedAccount) {
        Optional.ofNullable(request.getEmail()).ifPresent(existedAccount::setEmail);
        Optional.ofNullable(request.getFullName()).ifPresent(existedAccount::setFullName);
        Optional.ofNullable(request.getPhoneNumber()).ifPresent(existedAccount::setPhoneNumber);
        Optional.ofNullable(request.getUsername()).ifPresent(existedAccount::setUsername);
        return existedAccount;
    }

    @Override
    public Account updateAccount(AccountRequest request, Account existedAccount) {
        Optional.ofNullable(request.getStatus()).ifPresent(existedAccount::setStatus);
        Optional.ofNullable(request.getAccountRole()).ifPresent(existedAccount::setAccountRole);
        Optional.ofNullable(request.getFullName()).ifPresent(existedAccount::setFullName);
        Optional.ofNullable(request.getPhoneNumber()).ifPresent(existedAccount::setPhoneNumber);
        Optional.ofNullable(request.getUsername()).ifPresent(existedAccount::setUsername);
        return existedAccount;
    }

    @Override
    public AccountResponse toAccountResponse(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }
}
