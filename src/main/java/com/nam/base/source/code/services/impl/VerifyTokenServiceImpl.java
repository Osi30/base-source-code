package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.entities.VerifyToken;
import com.nam.base.source.code.repositories.VerifyTokenRepo;
import com.nam.base.source.code.services.VerifyTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerifyTokenServiceImpl implements VerifyTokenService {
    private final VerifyTokenRepo verifyTokenRepo;

    @Override
    public VerifyToken generateToken(String accountId) {
        VerifyToken token = VerifyToken.builder()
                .token(UUID.randomUUID().toString())
                .accountId(accountId)
                .expiryDate(LocalDateTime.now().plusDays(1))
                .build();
        return verifyTokenRepo.save(token);
    }
}
