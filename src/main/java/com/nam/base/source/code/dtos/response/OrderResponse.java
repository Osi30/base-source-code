package com.nam.base.source.code.dtos.response;

import com.nam.base.source.code.enums.OrderStatus;
import com.nam.base.source.code.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private OrderStatus status;
    private BigDecimal total;
    private PaymentMethod paymentMethod;

    // Account
}
