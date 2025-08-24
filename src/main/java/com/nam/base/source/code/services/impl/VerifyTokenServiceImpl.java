package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.entities.VerifyToken;
import com.nam.base.source.code.enums.AccountStatus;
import com.nam.base.source.code.exceptions.exceptions.AuthException;
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
    public VerifyToken generateToken(Account account) {
        VerifyToken existedToken = verifyTokenRepo.findByAccount_Id(account.getId());

        // Update token if existed
        if (existedToken != null) {
            existedToken.setToken(UUID.randomUUID().toString());
            existedToken.setExpiryDate(LocalDateTime.now().plusDays(1));
            return verifyTokenRepo.save(existedToken);
        }

        // Create new token
        VerifyToken token = VerifyToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plusDays(1))
                .account(account)
                .isVerified(false)
                .build();

        return verifyTokenRepo.save(token);
    }

    @Override
    public String verifyToken(String token) {
        VerifyToken verifyToken = verifyTokenRepo.findByToken(token);

        validateToken(verifyToken);

        if (verifyToken.getIsVerified()) {
            return "Token is already verified!";
        }

        verifyToken.setIsVerified(true);
        verifyToken.getAccount().setStatus(AccountStatus.ACTIVE);
        verifyTokenRepo.save(verifyToken);
        return "Token is verified!";
    }

    private void validateToken(VerifyToken verifyToken) {
        if (verifyToken == null) {
            throw new AuthException("Token is null");
        }
        if (verifyToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AuthException("Token is expired");
        }

    }
}
