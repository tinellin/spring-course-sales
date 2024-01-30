package com.training.tinelli.sales.repository;

import com.training.tinelli.sales.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findByNameLike(String name);
    boolean existsByName(String name);
}
