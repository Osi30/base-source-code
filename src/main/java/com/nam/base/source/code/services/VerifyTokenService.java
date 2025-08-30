package com.nam.base.source.code.services;

import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.entities.VerifyToken;

public interface VerifyTokenService {
    String verifyEmail(Account account);
    VerifyToken generateToken(Account account);
    String verifyToken(String token);
}
