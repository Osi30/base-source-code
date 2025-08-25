package com.nam.base.source.code.services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.dtos.response.TokenResponse;
import com.nam.base.source.code.enums.LoginType;
import com.nam.base.source.code.services.AuthService;
import com.nam.base.source.code.services.JwtService;
import com.nam.base.source.code.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse login(AuthRequest authRequest, LoginType loginType) {
        Authentication authentication = authenticate(authRequest, loginType);
        String accessToken = jwtService.generateToken(authentication);
        String refreshToken = refreshTokenService.generateRefreshToken(authentication);

        return TokenResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

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

    private Authentication authenticate(AuthRequest authRequest, LoginType loginType) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getIdentifier());

        switch (loginType) {
            case GOOGLE:
                // Do nothing
                break;
            default:
                if (userDetails == null) {
                    throw new BadCredentialsException("Account not found with identifier: " + authRequest.getIdentifier());
                }
                if (!passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())) {
                    throw new BadCredentialsException("Invalid password");
                }
                break;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
