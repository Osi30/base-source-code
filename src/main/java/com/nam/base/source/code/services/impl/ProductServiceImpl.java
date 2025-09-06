package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.dtos.request.ProductRequest;
import com.nam.base.source.code.dtos.response.ProductResponse;
import com.nam.base.source.code.entities.Account;
import com.nam.base.source.code.entities.OrderDetail;
import com.nam.base.source.code.entities.Product;
import com.nam.base.source.code.enums.ProductStatus;
import com.nam.base.source.code.exceptions.exceptions.ProductException;
import com.nam.base.source.code.mappers.ProductMapper;
import com.nam.base.source.code.repositories.ProductRepo;
import com.nam.base.source.code.services.AccountService;
import com.nam.base.source.code.services.ProductService;
import com.nam.base.source.code.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final AccountService accountService;
    private final ProductMapper productMapper;
    private final ProductRepo productRepo;

    private Product returnProductQuantity(Product product, int quantity) {
        product.setStock(product.getStock() + quantity);
        return product;
    }

    @Override
    public void returnProductQuantity(List<OrderDetail> orderDetails) {
        List<Product> products = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            products.add(returnProductQuantity(orderDetail.getProduct(), orderDetail.getQuantity()));
        }

        productRepo.saveAll(products);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        if (!ValidationUtils.isValidNumber(productRequest.getStock()) || productRequest.getStock() < 0) {
            productRequest.setStock(0);
            productRequest.setStatus(ProductStatus.OUT_OF_STOCK);
        }

        Account account = accountService.getAccountById(productRequest.getAccountId());

        Product product = productMapper.toProduct(productRequest);
        product.setAccount(account);

        return productMapper.toProductResponse(productRepo.save(product));
    }

    @Override
    public ProductResponse updateProduct(ProductRequest productRequest) {
        Product product = getProductById(productRequest.getId());
        product = productMapper.updateProduct(productRequest, product);
        return productMapper.toProductResponse(productRepo.save(product));
    }

    @Override
    public String deleteProduct(String productId) {
        Product product = getProductById(productId);
        product.setStatus(ProductStatus.DELETED);
        productRepo.save(product);
        return "Deleted product successfully";
    }

    @Override
    public String banProduct(String productId) {
        Product product = getProductById(productId);
        product.setStatus(ProductStatus.BANNED);
        productRepo.save(product);
        return "Banned product successfully";
    }

    @Override
    public Product getProductById(String productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found with id: " + productId));
    }

    @Override
    public ProductResponse getProductResponseById(String productId) {
        return productMapper.toProductResponse(getProductById(productId));
    }

    @Override
    public List<ProductResponse> getProducts() {
        return productRepo.findProductsByStatusIsNot(ProductStatus.DELETED)
                .stream().map(productMapper::toProductResponse)
                .toList();
    }
}
