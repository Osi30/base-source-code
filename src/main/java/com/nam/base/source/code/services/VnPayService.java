package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.response.OrderResponse;

import java.io.UnsupportedEncodingException;

public interface VnPayService {
    String createVNPayUrl(OrderResponse orderResponse) throws UnsupportedEncodingException;

}
