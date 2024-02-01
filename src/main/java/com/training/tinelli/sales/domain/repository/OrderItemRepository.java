package com.training.tinelli.sales.domain.repository;

import com.training.tinelli.sales.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
