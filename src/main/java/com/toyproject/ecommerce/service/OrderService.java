package com.toyproject.ecommerce.service;

import com.toyproject.ecommerce.controller.dto.CartForm;
import com.toyproject.ecommerce.controller.dto.CartOrderDto;
import com.toyproject.ecommerce.entity.Item;
import com.toyproject.ecommerce.entity.Member;
import com.toyproject.ecommerce.entity.Order;
import com.toyproject.ecommerce.entity.OrderItem;
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

    private final CartService cartService;

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

//        //주문한 상품은 장바구니에서 제거
//        deleteCartItem(cartOrderDto);

        return save.getId();

    }

    private void deleteCartItem(CartOrderDto cartOrderDto) {
        List<CartForm> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
        for (CartForm cartForm : cartOrderDtoList) {
            cartService.deleteCartItem(cartForm.getItemId());
        }
    }

    /**
     * 주문 목록 조회
     */

}
