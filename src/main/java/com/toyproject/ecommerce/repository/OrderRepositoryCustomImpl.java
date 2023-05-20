package com.toyproject.ecommerce.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toyproject.ecommerce.entity.*;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.toyproject.ecommerce.entity.QItem.*;
import static com.toyproject.ecommerce.entity.QMember.*;
import static com.toyproject.ecommerce.entity.QOrder.*;
import static com.toyproject.ecommerce.entity.QOrderItem.*;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrderRepositoryCustomImpl(EntityManager em) {
         this.queryFactory = new JPAQueryFactory(em);
    }

    // 주문 목록 조회 쿼리 (주문취소, 주문 완료 동적 쿼리 TODO)
    @Override
    public List<OrderDto> findOrdersByDto(Long memberId) {

        return queryFactory
                .select(new QOrderDto(
                        order.id,
                        item.name,
                        orderItem.count,
                        orderItem.orderPrice,
                        order.orderDate,
                        order.status
                ))
                .from(order)
                .join(order.orderItems, orderItem)
                .join(orderItem.item, item)
                .join(order.member, member)
                .where(member.id.eq(memberId))
                .fetch();
    }

}
