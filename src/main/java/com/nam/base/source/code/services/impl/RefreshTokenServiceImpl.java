package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.entities.RefreshToken;
import com.nam.base.source.code.repositories.AccountRepo;
import com.nam.base.source.code.repositories.RefreshTokenRepo;
import com.nam.base.source.code.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Value("${TTL_REFRESH_TOKEN}")
    private Long tokenTimeToLive;

    private final RefreshTokenRepo refreshTokenRepo;
    private final AccountRepo accountRepo;

    @Override
    public String generateRefreshToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accountId = userDetails.getUsername();
        RefreshToken existedToken = refreshTokenRepo.findByAccount_Id(accountId);

        // For another time token
        if (existedToken != null) {
            existedToken.setToken(UUID.randomUUID().toString());
            existedToken.setExpiresAt(LocalDateTime.now().plusSeconds(tokenTimeToLive));
            return refreshTokenRepo.save(existedToken).getToken();
        }

        // For first time token
        return refreshTokenRepo.save(RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .account(accountRepo.findAccountById(userDetails.getUsername()))
                .expiresAt(LocalDateTime.now().plusMinutes(tokenTimeToLive))
                .build()).getToken();
    }

    @Override
    public RefreshToken verifyRefreshToken(String refreshToken) {

        return null;
    }
}
