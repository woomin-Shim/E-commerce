package com.toyproject.ecommerce.service;


import com.toyproject.ecommerce.controller.dto.CartForm;
import com.toyproject.ecommerce.controller.dto.CartOrderDto;
import com.toyproject.ecommerce.entity.Item;
import com.toyproject.ecommerce.entity.Member;
import com.toyproject.ecommerce.entity.Order;
import com.toyproject.ecommerce.entity.OrderStatus;
import com.toyproject.ecommerce.repository.ItemRepository;
import com.toyproject.ecommerce.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("단일 주문 테스트")
    public void orderTest() {
        //given
        Member member = memberService.findMember(1L);
        Item item = Item.createItem("신발", "나이키 신발", 30000, 300);
        itemRepository.save(item);

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), 4);

        //then
        Order findOrder = orderRepository.findById(orderId).get();

        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(findOrder.getOrderItems().get(0).getCount()).isEqualTo(4);
        assertThat(findOrder.getTotalPrice()).isEqualTo(120000);
    }

    @Test
    @DisplayName("장바구니 상품들 주문 테스트 ")
    @Rollback(value = false)
    public void ordersTest() {
        //given
        Member member = memberService.findMember(1L);
        Item item1 = Item.createItem("신발", "나이키 신발", 30000, 300);
        Item item2 = Item.createItem("자켓", "프라다 자켓", 100000, 50);
        itemRepository.save(item1);
        itemRepository.save(item2);

        //신발 10개 주문
        CartForm cartForm1 = new CartForm();
        cartForm1.setItemId(item1.getId());
        cartForm1.setCount(10);

        //자켓 5개 주문
        CartForm cartForm2 = new CartForm();
        cartForm2.setItemId(item2.getId());
        cartForm2.setCount(5);

        //cartOrderDtoList 초기화 및 데이터 추가
        CartOrderDto cartOrderDto = new CartOrderDto();
        List<CartForm> cartOrderDtoList = new ArrayList<>();
        cartOrderDto.setCartOrderDtoList(cartOrderDtoList);
        cartOrderDtoList.add(cartForm1);
        cartOrderDtoList.add(cartForm2);


        //when
        Long orderId = orderService.orders(member.getId(), cartOrderDto);

        //then
        Order findOrder = orderRepository.findById(orderId).get();




        assertThat(findOrder.getTotalPrice()).isEqualTo(800000);  //(30000*10) + (100000*5)
        assertThat(findOrder.getMember()).isEqualTo(member);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(2);
        assertThat(findOrder.getOrderItems().get(1).getItem().getName()).isEqualTo("자켓");


    }
}