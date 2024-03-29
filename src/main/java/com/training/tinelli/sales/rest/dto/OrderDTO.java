package com.training.tinelli.sales.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderDTO {
    @NotNull(message = "{campo.codigo-cliente.obrigatorio}")
    private Integer client;

    @NotNull(message = "{campo.total-pedido.obrigatorio}")
    private BigDecimal total;

    private List<OrderItemDTO> items;
}
