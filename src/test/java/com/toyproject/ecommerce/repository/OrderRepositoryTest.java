package com.toyproject.ecommerce.repository;

import com.toyproject.ecommerce.controller.dto.CartForm;
import com.toyproject.ecommerce.controller.dto.CartOrderDto;
import com.toyproject.ecommerce.entity.*;
import com.toyproject.ecommerce.service.ItemService;
import com.toyproject.ecommerce.service.MemberService;
import com.toyproject.ecommerce.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("주문 목록 내역 테스트")
    public void orderViewTest() {

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

        List<OrderDto> result = orderRepository.findOrdersByDto(member.getId());
        for (OrderDto orderDto : result) {
            System.out.println("orderDto = " + orderDto);
        }

        //then
        assertThat(result.get(0).getOrderCount()).isEqualTo(10);  //신발 10개 주문 검증
        assertThat(result.size()).isEqualTo(2);  //주문 상품 수 검증
        assertThat(result.get(1).getOrderItemName()).isEqualTo("자켓");
    }

}