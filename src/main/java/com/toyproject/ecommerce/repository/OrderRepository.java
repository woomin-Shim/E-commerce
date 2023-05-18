package com.toyproject.ecommerce.repository;

import com.toyproject.ecommerce.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
