package com.nam.base.source.code.mappers;

import com.nam.base.source.code.dtos.response.OrderDetailResponse;
import com.nam.base.source.code.entities.OrderDetail;

public interface OrderDetailMapper {
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}
