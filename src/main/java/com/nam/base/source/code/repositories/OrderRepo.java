package com.nam.base.source.code.repositories;

import com.nam.base.source.code.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, String> {
    List<Order> getOrdersByAccountId(String accountId);
}
