package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.response.OrderResponse;
import com.stripe.exception.StripeException;

public interface StripeService {
    String createStripeUrl(OrderResponse orderResponse) throws StripeException;
}
