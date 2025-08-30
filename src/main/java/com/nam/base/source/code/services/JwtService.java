package com.nam.base.source.code.services;

import org.springframework.security.core.Authentication;

public interface JwtService {
    String generateToken(Authentication authentication);
    String getIdentifierFromToken(String token);
}
