package com.nam.base.source.code.controllers;

import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping
    public ResponseEntity<String> getVerifyToken() {
        Account account = new Account();
        if (account.getUsername().equals("admin")) {
            System.out.println("Oops");
        }
        return ResponseEntity.ok("value");
    }

    private Account getAccount() {
        return null;
    }
}
