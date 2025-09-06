package com.nam.base.source.code.controllers;

import com.nam.base.source.code.dtos.BaseResponse;
import com.nam.base.source.code.dtos.response.OrderDetailResponse;
import com.nam.base.source.code.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order/detail")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @PreAuthorize("hasAnyAuthority('TAKE_ORDER', 'MANAGE_ORDER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<BaseResponse> getOrderDetailsByOrderId(
            @PathVariable String orderId
    ) {
        List<OrderDetailResponse> responses = orderDetailService.getOrderDetailsByOrderId(orderId);

        BaseResponse baseResponse = BaseResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Get order successfully!")
                .data(responses)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
