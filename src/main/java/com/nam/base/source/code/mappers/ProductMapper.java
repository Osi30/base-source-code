package com.nam.base.source.code.mappers;

import com.nam.base.source.code.dtos.request.ProductRequest;
import com.nam.base.source.code.dtos.response.ProductResponse;
import com.nam.base.source.code.entities.Product;

public interface ProductMapper {
    Product toProduct(ProductRequest productRequest);
    Product updateProduct(ProductRequest productRequest, Product product);
    ProductResponse toProductResponse(Product product);
}
