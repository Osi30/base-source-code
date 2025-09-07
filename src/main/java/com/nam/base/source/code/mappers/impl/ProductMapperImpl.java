package com.nam.base.source.code.mappers.impl;

import com.nam.base.source.code.dtos.request.ProductRequest;
import com.nam.base.source.code.dtos.response.ProductResponse;
import com.nam.base.source.code.entities.Product;
import com.nam.base.source.code.enums.ProductStatus;
import com.nam.base.source.code.mappers.ProductMapper;
import com.nam.base.source.code.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {
    private final ModelMapper modelMapper;

    @Override
    public Product toProduct(ProductRequest productRequest) {
        Product product = modelMapper.map(productRequest, Product.class);
        product.setStatus(productRequest.getStatus() == null ? ProductStatus.INACTIVE : productRequest.getStatus());
        return product;
    }

    @Override
    public Product updateProduct(ProductRequest productRequest, Product product) {
        Optional.ofNullable(productRequest.getStatus()).ifPresent(product::setStatus);
        Optional.ofNullable(productRequest.getDescription()).ifPresent(product::setDescription);
        Optional.ofNullable(productRequest.getProductName()).ifPresent(product::setProductName);

        if (ValidationUtils.isValidNumber(productRequest.getStock())
                && productRequest.getStock() >= 0) {
            product.setStock(productRequest.getStock());
        } else {
            product.setStock(0);
        }

        if (ValidationUtils.isValidNumber(productRequest.getPrice())
                && productRequest.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            product.setPrice(productRequest.getPrice());
        } else {
            product.setPrice(BigDecimal.ZERO);
        }

        product.setStatus(product.getStock() == 0 ? ProductStatus.OUT_OF_STOCK : product.getStatus());

        return product;
    }

    @Override
    public ProductResponse toProductResponse(Product product) {
        ProductResponse response = modelMapper.map(product, ProductResponse.class);
        response.setAccountId(product.getAccount().getId());
        return response;
    }
}
