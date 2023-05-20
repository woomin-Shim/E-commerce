package com.toyproject.ecommerce.repository;

import com.toyproject.ecommerce.entity.Order;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderDto> findOrdersByDto(Long memberId);
}
