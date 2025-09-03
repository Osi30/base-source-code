package com.nam.base.source.code.controllers;

import com.nam.base.source.code.dtos.BaseResponse;
import com.nam.base.source.code.dtos.request.ProductRequest;
import com.nam.base.source.code.dtos.response.ProductResponse;
import com.nam.base.source.code.services.ProductService;
import com.nam.base.source.code.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final JwtService jwtService;

    @GetMapping("/list")
    public ResponseEntity<BaseResponse> getAllProducts() {
        List<ProductResponse> productResponses = productService.getProducts();

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get products successfully!")
                .data(productResponses)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<BaseResponse> getProductById(
            @PathVariable String productId
    ) {
        ProductResponse productResponse = productService.getProductResponseById(productId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get product successfully!")
                .data(productResponse)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    @PostMapping
    public ResponseEntity<BaseResponse> createProduct(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody ProductRequest productRequest
    ) {
        String accountId = jwtService.getIdentifierFromToken(token);
        productRequest.setAccountId(accountId);
        ProductResponse productResponse = productService.createProduct(productRequest);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Create product successfully!")
                .data(productResponse)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    @PutMapping
    public ResponseEntity<BaseResponse> updateProduct(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody ProductRequest productRequest
    ) {
        String accountId = jwtService.getIdentifierFromToken(token);
        productRequest.setAccountId(accountId);
        ProductResponse productResponse = productService.updateProduct(productRequest);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Update product successfully!")
                .data(productResponse)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('MANAGE_PRODUCT')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<BaseResponse> deleteProduct(
            @PathVariable String productId
    ) {
        String messageResponse = productService.deleteProduct(productId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message(messageResponse)
                .data(null)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('BAN_PRODUCT')")
    @DeleteMapping("/ban/{productId}")
    public ResponseEntity<BaseResponse> banProduct(
            @PathVariable String productId
    ) {
        String response = productService.banProduct(productId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Ban product successfully!")
                .data(response)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
