package com.toyproject.ecommerce.repository;

import com.toyproject.ecommerce.entity.OrderStatus;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderDto> findOrderDtos(Long memberId, OrderStatus orderStatus);

    List<OrderDto> findOrderDetail(Long memberId, OrderStatus orderStatus);
}
