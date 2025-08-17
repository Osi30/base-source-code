package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.entities.RefreshToken;
import com.nam.base.source.code.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {


    @Override
    public String generateRefreshToken(String userId) {
        return "";
    }

    @Override
    public RefreshToken verifyRefreshToken(String refreshToken) {
        return null;
    }
}
