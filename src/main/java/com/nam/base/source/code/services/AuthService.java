package com.nam.base.source.code.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.dtos.response.TokenResponse;

public interface AuthService {
    String register(AuthRequest authRequest);
    TokenResponse login(AuthRequest authRequest);
    String generateOauthURL(String loginType);
    String exchangeCodeForToken(String code) throws Exception;
    JsonNode getUserInfo(String accessToken) throws Exception;
}
