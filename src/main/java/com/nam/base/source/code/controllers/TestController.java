package com.nam.base.source.code.controllers;

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
    @Value("${JWT_HEADER}")
    private String value;

    @GetMapping
    public ResponseEntity<String> test() {

        return ResponseEntity.ok(value);
    }
}
