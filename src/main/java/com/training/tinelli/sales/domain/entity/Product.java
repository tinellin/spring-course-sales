package com.training.tinelli.sales.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
    public Product(String description, BigDecimal price) {
        this.description = description;
        this.price = price;
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description", nullable = false)
    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String description;

    @Column(name = "price")
    @NotNull(message = "{campo.preco.obrigatorio}")
    private BigDecimal price;
}
