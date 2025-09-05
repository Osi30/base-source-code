package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.request.OrderRequest;
import com.nam.base.source.code.dtos.response.OrderResponse;
import com.nam.base.source.code.entities.Order;

import java.util.List;

public interface OrderService {
    Order getOrderById(String orderId);
    OrderResponse getOrderResponseById(String orderId);
    OrderResponse createOrder(OrderRequest order);
    OrderResponse updateOrder(OrderRequest order);
    String cancelOrder(String orderId);
    List<OrderResponse> getOrders();
    List<OrderResponse> getOrdersByAccountId(String accountId);
}
