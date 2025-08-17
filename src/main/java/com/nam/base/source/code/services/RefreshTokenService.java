package com.nam.base.source.code.services;

import com.nam.base.source.code.entities.RefreshToken;

public interface RefreshTokenService {
    String generateRefreshToken(String userId);
    RefreshToken verifyRefreshToken(String refreshToken);
}
