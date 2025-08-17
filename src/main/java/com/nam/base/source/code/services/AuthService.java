package com.nam.base.source.code.services;

import com.fasterxml.jackson.databind.JsonNode;

public interface AuthService {
    String generateURL(String loginType);
    String exchangeCodeForToken(String code) throws Exception;
    JsonNode getUserInfo(String accessToken) throws Exception;
}
