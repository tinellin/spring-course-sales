package com.training.tinelli.sales.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderItemDTO {
    private Integer product;
    private Integer quantity;
}
