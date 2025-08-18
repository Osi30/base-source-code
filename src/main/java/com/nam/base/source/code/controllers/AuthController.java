package com.nam.base.source.code.controllers;

import com.nam.base.source.code.dtos.BaseResponse;
import com.nam.base.source.code.dtos.request.AuthRequest;
import com.nam.base.source.code.dtos.request.EmailRequest;
import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.entities.VerifyToken;
import com.nam.base.source.code.enums.EmailTemplate;
import com.nam.base.source.code.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;
    private final VerifyTokenService verifyTokenService;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(
            @RequestBody AuthRequest authRequest
    ) {
        // Save new account
        Account account = accountService.register(authRequest);

        // Generate email token for verification
        VerifyToken verifyToken = verifyTokenService.generateToken(account.getId());

        // Construct and send to account email
        emailService.sendHtmlEmail(EmailRequest.builder()
                .to(account.getEmail())
                .fullName(account.getFullName())
                .verifyToken(verifyToken.getToken())
                .emailTemplate(EmailTemplate.VERIFY_EMAIL)
                .build());

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Create account successfully! Please verify your email to login.")
                .data(null)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }


}
