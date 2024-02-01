package com.training.tinelli.sales.domain.repository;

import com.training.tinelli.sales.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "select o from Order o left join fetch o.items where o.id = :id")
    Optional<Order> findByIdFetchItems(@Param("id") Integer id);
}
