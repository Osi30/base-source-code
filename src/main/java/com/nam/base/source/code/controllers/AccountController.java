package com.nam.base.source.code.controllers;

import com.nam.base.source.code.dtos.BaseResponse;
import com.nam.base.source.code.dtos.request.AccountRequest;
import com.nam.base.source.code.dtos.response.AccountResponse;
import com.nam.base.source.code.services.AccountService;
import com.nam.base.source.code.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final JwtService jwtService;

    @GetMapping("/list")
    public ResponseEntity<BaseResponse> getAllAccounts() {
        List<AccountResponse> accountResponses = accountService.getAllAccountResponses();

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get accounts successfully!")
                .data(accountResponses)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<BaseResponse> getAccountById(
            @PathVariable String accountId
    ) {
        AccountResponse accountResponse = accountService.getAccountResponseById(accountId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get account successfully!")
                .data(accountResponse)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<BaseResponse> updateAccount(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody AccountRequest accountRequest
    ){
        String accountId = jwtService.getIdentifierFromToken(token);
        AccountResponse accountResponse = accountService.updateAccount(accountRequest, accountId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Update account successfully!")
                .data(accountResponse)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteAccount(
            @RequestHeader(value = "Authorization") String token
    ){
        String accountId = jwtService.getIdentifierFromToken(token);
        String messageResponse = accountService.deleteAccount(accountId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message(messageResponse)
                .data(null)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
