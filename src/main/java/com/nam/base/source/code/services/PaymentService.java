package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.response.OrderResponse;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface PaymentService {
    String createPayment(OrderResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;
    String handlePaymentCallback(Boolean isSuccess, String orderId);
}
