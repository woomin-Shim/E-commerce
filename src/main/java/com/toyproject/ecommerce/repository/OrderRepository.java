package com.toyproject.ecommerce.repository;

import com.toyproject.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

}
