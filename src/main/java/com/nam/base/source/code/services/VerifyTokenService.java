package com.nam.base.source.code.services;

import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.entities.VerifyToken;

public interface VerifyTokenService {
    VerifyToken generateToken(Account account);
    String verifyToken(String token);
}
