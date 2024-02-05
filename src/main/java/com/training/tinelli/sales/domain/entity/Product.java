package com.training.tinelli.sales.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Entity
@Table(name = "Product")
public class Product {
    public Product(String description, BigDecimal price) {
        this.description = description;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "description", nullable = false)
    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String description;

    @Column(name = "price")
    @NotNull(message = "{campo.preco.obrigatorio}")
    private BigDecimal price;
}
