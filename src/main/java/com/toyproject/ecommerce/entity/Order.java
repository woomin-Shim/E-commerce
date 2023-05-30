package com.toyproject.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;
    private LocalDateTime orderDate;
    private int totalPrice;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)  //Order.save 할 경우 orderItem도 같이 save TODO
    private List<OrderItem> orderItems = new ArrayList<>();

    private Order(Member member) {
        this.member = member;
        this.status = OrderStatus.ORDER;
        this.orderDate = LocalDateTime.now();
    }

    //==연관 관계 메서드==//
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.changeOrder(this);
    }


    public static Order createOrder(Member member, List<OrderItem> orderItems) {  //List<OrderItem> list??
        Order order = new Order(member);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        //총 주문 가격 추가
        order.totalPrice = order.getTotalPrice();

        return order;
    }

    //주문 취소
    public void cancelOrder() {
        this.status = OrderStatus.CANCEL;

        //상품 재고 수량 복귀
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }


    //== 전체 주문 가격 조회 ==/
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getOrderPrice();
        }
        return totalPrice;
    }
}
