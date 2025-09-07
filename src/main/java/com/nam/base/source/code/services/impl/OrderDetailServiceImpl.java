package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.dtos.dto.OrderDetailResult;
import com.nam.base.source.code.dtos.request.OrderDetailRequest;
import com.nam.base.source.code.dtos.response.OrderDetailResponse;
import com.nam.base.source.code.entities.Order;
import com.nam.base.source.code.entities.OrderDetail;
import com.nam.base.source.code.entities.Product;
import com.nam.base.source.code.enums.ProductStatus;
import com.nam.base.source.code.exceptions.exceptions.OrderException;
import com.nam.base.source.code.mappers.OrderDetailMapper;
import com.nam.base.source.code.repositories.OrderDetailRepo;
import com.nam.base.source.code.services.OrderDetailService;
import com.nam.base.source.code.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final ProductService productService;
    private final OrderDetailMapper orderDetailMapper;
    private final OrderDetailRepo orderDetailRepo;

    @Override
    public OrderDetail createOrderDetail(OrderDetailRequest request, Order order) {
        Product product = productService.getProductById(request.getProductId());

        // Throw exception if product owner buy there products
        if (product.getAccount().getId().equals(order.getAccount().getId())) {
            throw new OrderException("Cannot buy your own product!");
        }

        // Throw exception if product is inactive or out of stock
        if (!product.getStatus().equals(ProductStatus.ACTIVE)
                || product.getStock() < request.getQuantity()) {
            throw new OrderException("Product is inactive now or being out of stock!");
        }

        product.setStock(product.getStock() - request.getQuantity());

        return OrderDetail.builder()
                .quantity(request.getQuantity())
                .product(product)
                .order(order)
                .total(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
                .build();
    }

    @Override
    public OrderDetailResult createOrderDetails(List<OrderDetailRequest> requests, Order order) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderDetailRequest request : requests) {
            OrderDetail orderDetail = createOrderDetail(request, order);
            total = total.add(orderDetail.getTotal());
            orderDetails.add(orderDetail);
        }

        return OrderDetailResult.builder()
                .orderDetails(orderDetails)
                .totalPrice(total)
                .build();
    }

    @Override
    public List<OrderDetailResponse> getOrderDetailsByOrderId(String orderId) {
        return orderDetailRepo.findByOrderId(orderId)
                .stream().map(orderDetailMapper::toOrderDetailResponse)
                .toList();
    }
}
