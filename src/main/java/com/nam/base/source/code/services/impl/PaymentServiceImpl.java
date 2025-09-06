package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.dtos.response.OrderResponse;
import com.nam.base.source.code.services.MomoService;
import com.nam.base.source.code.services.PaymentService;
import com.nam.base.source.code.services.VnPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final VnPayService vnPayService;
    private final MomoService momoService;

    @Override
    public String createPayment(OrderResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        return switch (response.getPaymentMethod()) {
            case VNPAY -> vnPayService.createVNPayUrl(response);
            case MOMO -> momoService.createMomoUrl(response);
            default -> "Pay by cash successfully";
        };
    }

}
