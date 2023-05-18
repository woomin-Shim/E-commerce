package com.toyproject.ecommerce.service;

import com.toyproject.ecommerce.controller.dto.CartForm;
import com.toyproject.ecommerce.controller.dto.CartOrderDto;
import com.toyproject.ecommerce.domain.Item;
import com.toyproject.ecommerce.domain.Member;
import com.toyproject.ecommerce.domain.Order;
import com.toyproject.ecommerce.domain.OrderItem;
import com.toyproject.ecommerce.repository.ItemRepository;
import com.toyproject.ecommerce.repository.MemberRepository;
import com.toyproject.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 단일 주문
     */
    public Long order(Long memberId, Long itemId, int count) {

        List<OrderItem> orderItemList = new ArrayList<>();
        Item findItem = itemRepository.findById(itemId).orElseGet(() -> null);
        Member findMember = memberRepository.findById(memberId).orElseGet(() -> null);
        int orderPrice = findItem.getPrice() * count;

        OrderItem orderItem = OrderItem.createOrderItem(count, orderPrice, findItem);
        orderItemList.add(orderItem);
        Order order = Order.createOrder(findMember, orderItemList);

        Order save = orderRepository.save(order);
        return save.getId();

    }


    /**
     * 장바구니 상품들 주문
     */
    public Long orders(Long memberId, CartOrderDto cartOrderDto) {

        List<OrderItem> orderItemList = new ArrayList<>();
        Member findMember = memberRepository.findById(memberId).orElse(null);

        List<CartForm> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
        for (CartForm cartForm : cartOrderDtoList) {
            Item findItem = itemRepository.findById(cartForm.getItemId()).orElse(null);
            int orderPrice = findItem.getPrice() * cartForm.getCount();

            OrderItem orderItem = OrderItem.createOrderItem(cartForm.getCount(), orderPrice, findItem);
            orderItemList.add(orderItem);
        }

        Order order = Order.createOrder(findMember, orderItemList);

        Order save = orderRepository.save(order);
        return save.getId();

    }
}
