package com.nam.base.source.code.services;

import com.nam.base.source.code.entities.VerifyToken;

public interface VerifyTokenService {
    VerifyToken generateToken(String accountId);
}
