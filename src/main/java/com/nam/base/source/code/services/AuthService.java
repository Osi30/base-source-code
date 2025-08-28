package com.nam.base.source.code.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.dtos.response.TokenResponse;
import com.nam.base.source.code.enums.LoginType;

public interface AuthService {
    String register(AuthRequest authRequest);
    TokenResponse login(AuthRequest authRequest, LoginType loginType);
    String generateURL(String loginType);
    String exchangeCodeForToken(String code) throws Exception;
    JsonNode getUserInfo(String accessToken) throws Exception;
}
