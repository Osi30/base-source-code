package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.dto.OrderDetailResult;
import com.nam.base.source.code.dtos.request.OrderDetailRequest;
import com.nam.base.source.code.dtos.response.OrderDetailResponse;
import com.nam.base.source.code.entities.Order;
import com.nam.base.source.code.entities.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail createOrderDetail(OrderDetailRequest request, Order order);
    OrderDetailResult createOrderDetails(List<OrderDetailRequest> requests, Order order);
    List<OrderDetailResponse> getOrderDetailsByOrderId(String orderId);
}
