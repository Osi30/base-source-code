package com.nam.base.source.code.controllers;

import com.nam.base.source.code.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/momo/callback")
    public ResponseEntity<String> handleMomoCallback(@RequestBody Map<String, String> callbackData) {
        try {
            String requestId = callbackData.get("requestId");
            String orderId = callbackData.get("orderId");
            String resultCode = callbackData.get("resultCode");
            String message = callbackData.get("message");

//            momoPaymentService.handlePaymentCallback(requestId, orderId, resultCode, message);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
