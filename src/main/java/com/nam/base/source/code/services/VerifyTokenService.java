package com.nam.base.source.code.services;

import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.enums.TokenType;

import java.util.Map;

public interface VerifyTokenService {
    String sendToken(Account account, TokenType tokenType);
    String verifyToken(String token, Map<String, Object> data);
}
