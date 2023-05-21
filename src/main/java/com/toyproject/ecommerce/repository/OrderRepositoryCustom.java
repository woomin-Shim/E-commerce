package com.toyproject.ecommerce.repository;

import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderDto> findOrderDtos(Long memberId);
}
