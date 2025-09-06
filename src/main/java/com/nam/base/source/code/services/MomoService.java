package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.response.OrderResponse;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface MomoService {
    String createMomoUrl(OrderResponse order) throws NoSuchAlgorithmException, InvalidKeyException;
}
