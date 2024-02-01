package com.training.tinelli.sales.rest.controller;

import com.training.tinelli.sales.domain.entity.Order;
import com.training.tinelli.sales.domain.entity.OrderItem;
import com.training.tinelli.sales.exception.NotFoundException;
import com.training.tinelli.sales.rest.dto.OrderDTO;
import com.training.tinelli.sales.rest.dto.OrderInformationDTO;
import com.training.tinelli.sales.rest.dto.OrderItemInformationDTO;
import com.training.tinelli.sales.rest.dto.OrderStatusUpdateDTO;
import com.training.tinelli.sales.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v0/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer saveOrder(@RequestBody OrderDTO orderDTO) {
        Order order = orderService.saveOrder(orderDTO);
        return order.getId();
    }

    @GetMapping("/{id}")
    public OrderInformationDTO getOrderById(@PathVariable Integer id) {
        return orderService.getFullOrderById(id).map(order ->
                OrderInformationDTO.builder()
                .id(order.getId())
                .cpf(order.getClient().getCpf())
                .clientName(order.getClient().getName())
                .orderDate(order.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .total(order.getTotal())
                .status(order.getStatus().name())
                .items(convertToOrdemItemInformationDTO(order.getItems()))
                .build()
        ).orElseThrow(() -> new NotFoundException("Pedido não encontrado."));
    }
    
    @PatchMapping("/{id}")
    private void updateStatus(@PathVariable Integer id, @RequestBody OrderStatusUpdateDTO dto) {
        orderService.updateOrderStatus(id, dto);
    }

    private Set<OrderItemInformationDTO> convertToOrdemItemInformationDTO(List<OrderItem> items) {

        /* boa prática retornar uma lista vazia ao invés de null */
        if (CollectionUtils.isEmpty(items))
            return Collections.emptySet();

        return items.stream().map(orderItem ->
                OrderItemInformationDTO.builder()
                        .productDescription(orderItem.getProduct().getDescription())
                        .productUnitPrice(orderItem.getProduct().getPrice())
                        .quantity(orderItem.getQuantity())
                        .build()
        ).collect(Collectors.toSet());
    }
}
