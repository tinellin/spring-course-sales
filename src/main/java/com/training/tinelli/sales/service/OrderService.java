package com.training.tinelli.sales.service;

import com.training.tinelli.sales.domain.entity.Order;
import com.training.tinelli.sales.rest.dto.OrderDTO;
import com.training.tinelli.sales.rest.dto.OrderStatusUpdateDTO;

import java.util.Optional;

public interface OrderService {
    Order saveOrder(OrderDTO orderDTO);

    Optional<Order> getFullOrderById(Integer id);

    void updateOrderStatus(Integer id, OrderStatusUpdateDTO dto);
}
