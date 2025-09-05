package com.nam.base.source.code.controllers;

import com.nam.base.source.code.dtos.BaseResponse;
import com.nam.base.source.code.dtos.request.OrderRequest;
import com.nam.base.source.code.dtos.response.OrderResponse;
import com.nam.base.source.code.services.AccountService;
import com.nam.base.source.code.services.JwtService;
import com.nam.base.source.code.services.OrderService;
import com.nam.base.source.code.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final AccountService accountService;
    private final PaymentService paymentService;
    private final JwtService jwtService;

    @PreAuthorize("hasAuthority('MANAGE_ORDER')")
    @GetMapping("/list")
    public ResponseEntity<BaseResponse> getAllOrders() {
        List<OrderResponse> orderResponses = orderService.getOrders();

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get orders successfully!")
                .data(orderResponses)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/list/account")
    public ResponseEntity<BaseResponse> getAllOrdersByAccount(
            @RequestHeader(value = "Authorization") String token
    ) {
        String accountId = jwtService.getIdentifierFromToken(token);
        List<OrderResponse> orderResponses = orderService.getOrdersByAccountId(accountId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get orders successfully!")
                .data(orderResponses)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<BaseResponse> getOrderById(
            @PathVariable String orderId
    ) {
        OrderResponse orderResponse = orderService.getOrderResponseById(orderId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get order successfully!")
                .data(orderResponse)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('TAKE_ORDER')")
    @PostMapping
    public ResponseEntity<BaseResponse> createOrder(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody OrderRequest orderRequest
    ) throws UnsupportedEncodingException {
        String accountId = jwtService.getIdentifierFromToken(token);
        orderRequest.setAccount(accountService.getAccountById(accountId));
        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        String paymentURL = paymentService.createPaymentUrl(orderResponse);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Create order successfully!")
                .data(paymentURL)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('TAKE_ORDER')")
    @PutMapping
    public ResponseEntity<BaseResponse> updateOrder(
            @RequestHeader(value = "Authorization") String token,
            @RequestBody OrderRequest orderRequest
    ) {
        String accountId = jwtService.getIdentifierFromToken(token);
        orderRequest.setAccount(accountService.getAccountById(accountId));
        OrderResponse orderResponse = orderService.updateOrder(orderRequest);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Update order successfully!")
                .data(orderResponse)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('TAKE_ORDER')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<BaseResponse> cancelOrder(
            @PathVariable String orderId
    ) {
        String messageResponse = orderService.cancelOrder(orderId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message(messageResponse)
                .data(null)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
