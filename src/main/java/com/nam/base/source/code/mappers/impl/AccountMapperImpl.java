package com.nam.base.source.code.mappers.impl;

import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.exceptions.exceptions.AuthException;
import com.nam.base.source.code.mappers.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountMapperImpl implements AccountMapper {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthRequest toAccountDTO(Account account) {
//        Account account1
        return null;
    }

    @Override
    public Account toAccount(AuthRequest authRequest) {
        Account account = modelMapper.map(authRequest, Account.class);

        if (authRequest.getPassword() == null) {
            throw new AuthException("Password is required");
        }
        account.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        account.setStatus(true);
        account.setIsVerified(false);

//        account.setAccountRole(authRequest.getAccountRoleId() == null
//        ? AccountRole.CUSTOMER : authRequest.getAccountRoleId());

        return account;
    }
}
