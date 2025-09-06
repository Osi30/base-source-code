package com.nam.base.source.code.controllers;

import com.nam.base.source.code.dtos.BaseResponse;
import com.nam.base.source.code.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/vnpay/callback")
    public ResponseEntity<BaseResponse> handleVnPayCallback(@RequestBody Map<String, String> params) {
        String responseCode = params.get("vnp_ResponseCode");
        String transactionStatus = params.get("vnp_TransactionStatus");
        String orderId = params.get("vnp_OrderInfo");

        String response = paymentService.handlePaymentCallback("00".equals(responseCode) && "00".equals(transactionStatus), orderId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("VnPay Callback")
                .data(response)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/momo/callback")
    public ResponseEntity<BaseResponse> handleMomoCallback(@RequestBody Map<String, String> callbackData) {
        String orderId = callbackData.get("orderId");
        String resultCode = callbackData.get("resultCode");
        String message = callbackData.get("message");

        String response = paymentService.handlePaymentCallback("0".equals(resultCode), orderId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Momo Callback: " + message)
                .data(response)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/cash/callback")
    public ResponseEntity<BaseResponse> handleCashCallback(@RequestBody Map<String, String> callbackData) {
        String orderId = callbackData.get("orderId");
        String isSuccess = callbackData.get("isSuccess");

        String response = paymentService.handlePaymentCallback("0".equals(isSuccess), orderId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Cash Callback")
                .data(response)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
