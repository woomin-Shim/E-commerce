package com.toyproject.ecommerce.repository;

import com.querydsl.core.annotations.QueryProjection;
import com.toyproject.ecommerce.entity.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//주문 목록 DTO
@Data
@EqualsAndHashCode(of = "orderId")
public class OrderDto {

    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItemDtoList;


    @QueryProjection
    public OrderDto(Long orderId, LocalDateTime orderDate, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }
}
