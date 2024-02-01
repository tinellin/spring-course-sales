package com.training.tinelli.sales.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderDTO {
    private Integer client;
    private BigDecimal total;
    private List<OrderItemDTO> items;
}
