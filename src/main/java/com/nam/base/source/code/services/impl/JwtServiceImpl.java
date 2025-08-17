package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {


    @Override
    public String generateToken(Authentication authentication, Account account) {
        return "";
    }

    @Override
    public Account getAccountFromToken(String token) {
        return null;
    }
}
