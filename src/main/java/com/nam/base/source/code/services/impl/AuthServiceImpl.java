package com.nam.base.source.code.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam.base.source.code.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    @Override
    public String generateURL(String loginType) {
        return "";
    }

    @Override
    public String exchangeCodeForToken(String code) throws Exception {
        return "";
    }

    @Override
    public JsonNode getUserInfo(String accessToken) throws Exception {
        return null;
    }
}
