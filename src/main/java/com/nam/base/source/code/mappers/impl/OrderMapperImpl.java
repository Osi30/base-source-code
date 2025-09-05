package com.nam.base.source.code.mappers.impl;

import com.nam.base.source.code.dtos.request.OrderRequest;
import com.nam.base.source.code.dtos.response.OrderResponse;
import com.nam.base.source.code.entities.Order;
import com.nam.base.source.code.enums.OrderStatus;
import com.nam.base.source.code.mappers.OrderMapper;
import com.nam.base.source.code.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderMapperImpl implements OrderMapper {
    private final ModelMapper modelMapper;

    @Override
    public Order toOrder(OrderRequest orderRequest) {
        Order order = modelMapper.map(orderRequest, Order.class);
        order.setStatus(OrderStatus.AWAITING_PAYMENT);
        return order;
    }

    @Override
    public OrderResponse toOrderResponse(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public Order updateOrder(Order order, OrderRequest orderRequest) {
        Optional.ofNullable(orderRequest.getEmail()).ifPresent(order::setEmail);
        Optional.ofNullable(orderRequest.getFullName()).ifPresent(order::setFullName);
        Optional.ofNullable(orderRequest.getPhoneNumber()).ifPresent(order::setPhoneNumber);
        Optional.ofNullable(orderRequest.getPaymentMethod()).ifPresent(order::setPaymentMethod);
        return order;
    }
}
