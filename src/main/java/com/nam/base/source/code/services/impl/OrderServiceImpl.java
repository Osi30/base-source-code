package com.nam.base.source.code.services.impl;

import com.nam.base.source.code.dtos.dto.OrderDetailResult;
import com.nam.base.source.code.dtos.request.OrderRequest;
import com.nam.base.source.code.dtos.response.OrderResponse;
import com.nam.base.source.code.entities.Order;
import com.nam.base.source.code.enums.OrderStatus;
import com.nam.base.source.code.exceptions.exceptions.OrderException;
import com.nam.base.source.code.mappers.OrderMapper;
import com.nam.base.source.code.repositories.OrderRepo;
import com.nam.base.source.code.services.OrderDetailService;
import com.nam.base.source.code.services.OrderService;
import com.nam.base.source.code.services.ProductService;
import com.nam.base.source.code.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDetailService orderDetailService;
    private final ProductService productService;
    private final OrderMapper orderMapper;
    private final OrderRepo orderRepo;

    @Override
    public Order getOrderById(String orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found with id: " + orderId));
    }

    @Override
    public OrderResponse getOrderResponseById(String orderId) {
        return orderMapper.toOrderResponse(getOrderById(orderId));
    }

    @CacheEvict(value = {"orders"}, allEntries = true)
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = orderMapper.toOrder(orderRequest);

        OrderDetailResult orderDetailResult = orderDetailService.createOrderDetails(orderRequest.getDetails(), order);
        order.setTotal(orderDetailResult.getTotalPrice());
        order.setOrderDetails(orderDetailResult.getOrderDetails());

        return orderMapper.toOrderResponse(orderRepo.save(order));
    }

    @CacheEvict(value = {"orders"}, allEntries = true)
    @Override
    public OrderResponse updateOrder(OrderRequest orderRequest) {
        Order orderToUpdate = getOrderById(orderRequest.getId());

        orderToUpdate = orderMapper.updateOrder(orderToUpdate, orderRequest);

        if (ValidationUtils.isValidCollection(orderToUpdate.getOrderDetails())) {
            OrderDetailResult orderDetailResult = orderDetailService.createOrderDetails(orderRequest.getDetails(), orderToUpdate);
            orderToUpdate.setTotal(orderDetailResult.getTotalPrice());
            orderToUpdate.setOrderDetails(orderDetailResult.getOrderDetails());
        }

        return orderMapper.toOrderResponse(orderRepo.save(orderToUpdate));
    }

    @CacheEvict(value = {"orders"}, allEntries = true)
    @Override
    public String cancelOrder(String orderId) {
        Order orderToCancel = getOrderById(orderId);
        validateBeforeUpdateStatus(orderToCancel);
        orderToCancel.setStatus(OrderStatus.CANCELLED);

        // Add product quantity again
        productService.returnProductQuantity(orderToCancel.getOrderDetails());

        orderRepo.save(orderToCancel);

        return "Order cancelled successfully";
    }

    private void validateBeforeUpdateStatus(Order order) {
        if (!order.getStatus().equals(OrderStatus.AWAITING_PAYMENT)) {
            throw new OrderException("Invalid order status");
        }
    }

    @CacheEvict(value = {"orders"}, allEntries = true)
    @Override
    public String completeOrder(String orderId) {
        Order orderToComplete = getOrderById(orderId);
        validateBeforeUpdateStatus(orderToComplete);
        orderToComplete.setStatus(OrderStatus.COMPLETED);
        orderRepo.save(orderToComplete);

        return "Order completed successfully";
    }

    @Cacheable(value = "orders")
    @Override
    public List<OrderResponse> getOrders() {
        return orderRepo.findAll()
                .stream().map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getOrdersByAccountId(String accountId) {
        return orderRepo.getOrdersByAccountId(accountId)
                .stream().map(orderMapper::toOrderResponse)
                .toList();
    }
}
