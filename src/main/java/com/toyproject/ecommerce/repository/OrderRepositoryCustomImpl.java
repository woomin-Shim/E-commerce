package com.toyproject.ecommerce.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toyproject.ecommerce.entity.*;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.toyproject.ecommerce.entity.QItem.*;
import static com.toyproject.ecommerce.entity.QItemImage.*;
import static com.toyproject.ecommerce.entity.QMember.*;
import static com.toyproject.ecommerce.entity.QOrder.*;
import static com.toyproject.ecommerce.entity.QOrderItem.*;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryCustomImpl(EntityManager em) {
         this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderDto> findOrderDetail(Long memberId) {

        List<OrderDto> orderDtos = findOrderDtos(memberId);

        orderDtos.forEach(o-> {
            List<OrderItemDto> findOrderItemDto = findOrderItemDtos(o.getOrderId());
            o.setOrderItemDtoList(findOrderItemDto);
        });

        return orderDtos;
    }

    // 주문 목록 조회 쿼리 (주문취소, 주문 완료 동적 쿼리 TODO)
    @Override
    public List<OrderDto> findOrderDtos(Long memberId) {

        return queryFactory
                .select(new QOrderDto(
                        order.id,
                        order.orderDate,
                        order.status
                ))
                .from(order)
                .join(order.member, member)
                .where(member.id.eq(memberId))
                .fetch();
    }

    private List<OrderItemDto> findOrderItemDtos(Long orderId) {

        return queryFactory
                .select(Projections.constructor(OrderItemDto.class,
                        item.name,
                        orderItem.orderPrice,
                        orderItem.count
//                        itemImage.storeName
                ))
                .from(orderItem)
                .join(orderItem.item, item)
//                .join(item.itemImageList, itemImage)
                .where(orderItem.order.id.eq(orderId)
//                        itemImage.firstImage.eq("Y")  //대표 상품 이미지만 고르기!
                )
                .fetch();
    }




}
