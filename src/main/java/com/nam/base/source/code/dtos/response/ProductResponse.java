package com.nam.base.source.code.dtos.response;

import com.nam.base.source.code.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String id;
    private String productName;
    private String description;
    private ProductStatus status;
    private int stock;
    private String accountId;
}
