package com.training.tinelli.sales.repository;

import com.training.tinelli.sales.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItem extends JpaRepository<Order, Integer> {
}
