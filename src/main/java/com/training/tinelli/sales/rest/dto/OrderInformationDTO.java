package com.training.tinelli.sales.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInformationDTO {
    private Integer id;
    private String cpf;
    private String clientName;
    private String orderDate;
    private BigDecimal total;
    private String status;
    private List<OrderItemInformationDTO> items;
}
