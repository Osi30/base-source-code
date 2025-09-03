package com.nam.base.source.code.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nam.base.source.code.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    @JsonProperty("productId")
    private String id;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("status")
    private ProductStatus status;

    @JsonProperty("stock")
    private int stock;

    @JsonProperty("accountId")
    private String accountId;
}
