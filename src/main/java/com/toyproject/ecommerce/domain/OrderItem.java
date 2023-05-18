package com.toyproject.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;
    private int count;  //주문 수량
    private int orderPrice;  //주문 상품 가격

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private OrderItem(int count, int orderPrice, Item item) {
        this.count = count;
        this.orderPrice = orderPrice;
        this.item = item;
    }

    public void changeOrder(Order order) {
        this.order = order;
    }

    public static OrderItem createOrderItem(int count, int orderPrice, Item item) {
        return new OrderItem(count, orderPrice, item);
    }

//    public int getTotalPrice() {
//        return getOrderPrice() * getCount();
//    }

}
