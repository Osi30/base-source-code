package com.nam.base.source.code.mappers;

import com.nam.base.source.code.dtos.request.OrderRequest;
import com.nam.base.source.code.dtos.response.OrderResponse;
import com.nam.base.source.code.entities.Order;

public interface OrderMapper {
    Order toOrder(OrderRequest orderRequest);
    OrderResponse toOrderResponse(Order order);
    Order updateOrder(Order order, OrderRequest orderRequest);
}
