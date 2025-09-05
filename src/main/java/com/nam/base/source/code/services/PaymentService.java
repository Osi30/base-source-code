package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.response.OrderResponse;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface PaymentService {
    String createPaymentUrl(OrderResponse orderResponse) throws UnsupportedEncodingException;
}
