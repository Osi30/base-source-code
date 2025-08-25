package com.nam.base.source.code.services;

import com.nam.base.source.code.entities.RefreshToken;
import org.springframework.security.core.Authentication;

public interface RefreshTokenService {
    String generateRefreshToken(Authentication authentication);
    RefreshToken verifyRefreshToken(String refreshToken);
}
