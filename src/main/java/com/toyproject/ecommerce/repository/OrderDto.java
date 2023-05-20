package com.toyproject.ecommerce.repository;

import com.querydsl.core.annotations.QueryProjection;
import com.toyproject.ecommerce.entity.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//주문 목록 DTO
@Data
public class OrderDto {

    private Long orderId;
    private String orderItemName;
    private int orderCount;
    private int orderPrice;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;


    @QueryProjection
    public OrderDto(Long orderId, String orderItemName, int orderCount, int orderPrice, LocalDateTime orderDate, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderItemName = orderItemName;
        this.orderCount = orderCount;
        this.orderPrice = orderPrice;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }
}
