package com.nam.base.source.code.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
    @JsonProperty("orderDetailId")
    private String id;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("productId")
    private String productId;
}
