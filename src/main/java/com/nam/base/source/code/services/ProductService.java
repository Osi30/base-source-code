package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.request.ProductRequest;
import com.nam.base.source.code.dtos.response.ProductResponse;
import com.nam.base.source.code.entities.OrderDetail;
import com.nam.base.source.code.entities.Product;

import java.util.List;

public interface ProductService {
    void returnProductQuantity(List<OrderDetail> orderDetails);
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(ProductRequest productRequest);
    String deleteProduct(String productId);
    String banProduct(String productId);
    Product getProductById(String productId);
    ProductResponse getProductResponseById(String productId);
    List<ProductResponse> getProducts();
}
