package com.nam.base.source.code.services;

import com.nam.base.source.code.entities.Account;
import org.springframework.security.core.Authentication;

public interface JwtService {
    String generateToken(Authentication authentication, Account account);
    Account getAccountFromToken(String token);
}
