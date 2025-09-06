package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.dtos.response.OrderResponse;
import com.nam.base.source.code.entities.Order;
import com.nam.base.source.code.enums.OrderStatus;
import com.nam.base.source.code.exceptions.exceptions.PaymentException;
import com.nam.base.source.code.services.MomoService;
import com.nam.base.source.code.services.OrderService;
import com.nam.base.source.code.services.PaymentService;
import com.nam.base.source.code.services.VnPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final VnPayService vnPayService;
    private final MomoService momoService;
    private final OrderService orderService;

    @Override
    public String createPayment(OrderResponse response) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        return switch (response.getPaymentMethod()) {
            case VNPAY -> vnPayService.createVNPayUrl(response);
            case MOMO -> momoService.createMomoUrl(response);
            default -> "Pay by cash successfully";
        };
    }

    @Override
    public String handlePaymentCallback(Boolean isSuccess, String orderId) {
        if (isSuccess) {
            return orderService.completeOrder(orderId);
        } else {
            return orderService.cancelOrder(orderId);
        }
    }
}
