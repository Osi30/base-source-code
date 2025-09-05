package com.nam.base.source.code.dtos.dto;

import com.nam.base.source.code.entities.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResult {
    private BigDecimal totalPrice;
    private List<OrderDetail> orderDetails;
}
