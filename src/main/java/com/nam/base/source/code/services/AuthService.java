package com.nam.base.source.code.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.dtos.request.RefreshTokenRequest;
import com.nam.base.source.code.dtos.response.TokenResponse;

public interface AuthService {
    String register(AuthRequest authRequest);

    String logout(String accessToken);

    String requestResetPassword(String email);

    TokenResponse login(AuthRequest authRequest);

    TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    String generateOauthURL(String loginType);

    String exchangeCodeForToken(String code) throws JsonProcessingException;

    JsonNode getUserInfo(String accessToken) throws JsonProcessingException;
}
