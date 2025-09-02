package com.nam.base.source.code.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.dtos.request.RefreshTokenRequest;
import com.nam.base.source.code.dtos.response.TokenResponse;
import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.entities.RefreshToken;
import com.nam.base.source.code.enums.AccountIdentifier;
import com.nam.base.source.code.enums.AccountStatus;
import com.nam.base.source.code.enums.AuthType;
import com.nam.base.source.code.enums.TokenType;
import com.nam.base.source.code.exceptions.exceptions.AuthException;
import com.nam.base.source.code.services.*;
import com.nam.base.source.code.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;

    @Value("${GOOGLE_REDIRECT_URI}")
    private String googleRedirectUri;

    @Value("${GOOGLE_CLIENT_SECRET}")
    private String googleClientSecret;

    private final JwtService jwtService;
    private final AccountService accountService;
    private final UserDetailsService userDetailsService;
    private final VerifyTokenService verifyTokenService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public String register(AuthRequest authRequest) {
        // Validation
        if (ValidationUtils.isNullOrEmpty(authRequest.getUsername())
                && ValidationUtils.isNullOrEmpty(authRequest.getEmail())
                && ValidationUtils.isNullOrEmpty(authRequest.getPhoneNumber())) {
            throw new BadCredentialsException("Required at least one field of username, email, phoneNumber");
        }

        // Create account
        Account account = accountService.createAccount(authRequest);

        StringBuilder message = new StringBuilder("Create account successfully!");

        if (account.getEmail() != null) {
            // Generate and send email token for verification
            String verifyEmailMessage = verifyTokenService.sendToken(account, TokenType.VERIFY_EMAIL);
            message.append(verifyEmailMessage);
        }

        return message.toString();
    }

    @Override
    public String logout(String accessToken) {
        String accountId = jwtService.getIdentifierFromToken(accessToken);
        return refreshTokenService.deleteRefreshToken(accountId);
    }

    @Override
    public String requestResetPassword(String email) {
        Account account = accountService.getAccountByIdentifier(email, AccountIdentifier.EMAIL);
        if (account == null) {
            throw new AuthException("Reset Password Failed");
        }

        return verifyTokenService.sendToken(account, TokenType.RESET_PASSWORD);
    }

    @Override
    public TokenResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticate(authRequest, authRequest.getAuthType());
        String accessToken = jwtService.generateToken(authentication);
        String refreshToken = refreshTokenService.generateRefreshToken(authentication);

        return TokenResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        Account account = refreshToken.getAccount();
        AuthRequest authRequest = AuthRequest.builder()
                .id(account.getId())
                .authType(AuthType.REFRESH_TOKEN)
                .password(account.getPassword())
                // Missing roles
                .build();
        return login(authRequest);
    }

    /// Creates URL that redirects the user to Google's authorization server.
    @Override
    public String generateOauthURL(String loginType) {
        if (loginType.equalsIgnoreCase("google")) {
            String state = UUID.randomUUID().toString();
            String scope = "profile email";
            return "https://accounts.google.com/o/oauth2/auth" +
                    "?client_id=" + googleClientId +
                    "&redirect_uri=" + URLEncoder.encode(googleRedirectUri
                    , StandardCharsets.UTF_8) +
                    "&response_type=code" +
                    "&scope=" + URLEncoder.encode(scope, StandardCharsets.UTF_8) +
                    "&state=" + state;
        } else {
            return null;
        }
    }

    /// Exchange authorization code from user for access token to user google's account
    @Override
    public String exchangeCodeForToken(String code) throws JsonProcessingException {
        // 1. Google's API token endpoint
        String tokenUrl = "https://oauth2.googleapis.com/token";
        // 2. Setup Header that the request body will be sent as URL-encoded form data.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 3. Setup Request Parameters
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("code", code);
        map.add("client_id", googleClientId);
        map.add("client_secret", googleClientSecret);
        map.add("redirect_uri", googleRedirectUri);
        map.add("grant_type", "authorization_code");
        // 4. Create object representing HTTP request
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        // 5. Send POST request to token URL
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
        // 6. Parse JSON response body to JsonNode object
        JsonNode responseJson = objectMapper.readTree(response.getBody());
        // 7. Return accessToken as a String
        return responseJson.get("access_token").asText();
    }

    /// Get user google account info by sending access token
    @Override
    public JsonNode getUserInfo(String accessToken) throws JsonProcessingException {
        // 1. Google's API user info endpoint
        String userInfoUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
        // 2. Setup Request Header with Access Token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        // 3. Create object representing HTTP request
        HttpEntity<String> request = new HttpEntity<>(headers);
        // 5. Send GET request to URL
        ResponseEntity<String> response = restTemplate.exchange(userInfoUrl
                , HttpMethod.GET, request, String.class);
        // 6. Parse JSON response body to JsonNode object and return JSON response body
        return objectMapper.readTree(response.getBody());
    }

    private Authentication authenticate(AuthRequest authRequest, AuthType authType) {
        UserDetails userDetails;

        switch (authType) {
            case GOOGLE:
                Account googleAccount = accountService.getAccountByIdentifier(authRequest.getEmail(), AccountIdentifier.EMAIL);

                // Create account if not exist one
                if (googleAccount == null) {
                    authRequest.setAccountStatus(AccountStatus.ACTIVE);
                    googleAccount = accountService.createAccount(authRequest);
                }

                userDetails = new User(googleAccount.getId(), "", googleAccount.getAuthorities());
                break;
            case REFRESH_TOKEN:
                Account account = accountService.getAccountById(authRequest.getId());
                if (authRequest.getPassword() == null) {
                    authRequest.setPassword("");
                }
                userDetails = new User(authRequest.getId(), authRequest.getPassword(), account.getAuthorities());
                break;
            default:
                userDetails = userDetailsService.loadUserByUsername(authRequest.getIdentifier());
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
