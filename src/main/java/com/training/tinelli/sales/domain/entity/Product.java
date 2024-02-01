package com.training.tinelli.sales.domain.entity;

import jakarta.persistence.*;
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
    private String description;

    @Column(name = "price")
    private BigDecimal price;
}
