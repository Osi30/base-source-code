package com.nam.base.source.code.mappers.impl;

import com.nam.base.source.code.dtos.response.OrderDetailResponse;
import com.nam.base.source.code.entities.OrderDetail;
import com.nam.base.source.code.mappers.OrderDetailMapper;
import com.nam.base.source.code.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailMapperImpl implements OrderDetailMapper {
    private final ModelMapper modelMapper;
    private final ProductMapper productMapper;

    @Override
    public OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
        OrderDetailResponse orderDetailResponse = modelMapper.map(orderDetail, OrderDetailResponse.class);
        orderDetailResponse.setProduct(productMapper.toProductResponse(orderDetail.getProduct()));
        return orderDetailResponse;
    }
}
