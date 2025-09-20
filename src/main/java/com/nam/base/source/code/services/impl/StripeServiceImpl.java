package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.dtos.response.OrderResponse;
import com.nam.base.source.code.services.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {
    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @Value("${SUCCESS_URL}")
    private String successUrl;

    @Value("${CANCEL_URL}")
    private String cancelUrl;


    @Override
    public String createStripeUrl(OrderResponse order) throws StripeException {
        String productName = order.getId();
        String currency = "VND";
        Long total = order.getTotal().longValue();
        Long quantity = 1L;

        Stripe.apiKey = secretKey;

        // Create a PaymentIntent with the order amount and currency
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(productName)
                        .build();

        // Create new line item with the above product data and associated price
        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(currency)
                        .setUnitAmount(total)
                        .setProductData(productData)
                        .build();

        // Create new line item with the above price data
        SessionCreateParams.LineItem lineItem =
                SessionCreateParams
                        .LineItem.builder()
                        .setQuantity(quantity)
                        .setPriceData(priceData)
                        .build();

        // Create new session with the line items
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(successUrl)
                        .setCancelUrl(cancelUrl)
                        .addLineItem(lineItem)
                        .build();

        // Create new session
        Session session = Session.create(params);
        if (session != null) {
            return session.getUrl();
        }
        return null;
    }
}
