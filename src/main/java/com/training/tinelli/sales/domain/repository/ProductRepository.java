package com.training.tinelli.sales.domain.repository;

import com.training.tinelli.sales.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
