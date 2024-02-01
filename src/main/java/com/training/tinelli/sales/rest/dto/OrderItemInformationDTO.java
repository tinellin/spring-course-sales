package com.training.tinelli.sales.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemInformationDTO {
    private String productDescription;
    private BigDecimal productUnitPrice;
    private Integer quantity;
}
