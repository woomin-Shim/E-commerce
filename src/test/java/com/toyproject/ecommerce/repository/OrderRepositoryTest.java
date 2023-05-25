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
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

        orderService.order(member.getId(), item1.getId(), 30);

        List<OrderDto> result = orderRepository.findOrderDtos(member.getId());
        for (OrderDto orderDto : result) {
            System.out.println("orderDto = " + orderDto);
        }

        //then
        assertThat(result.size()).isEqualTo(3);  //주문 상품 수 검증
    }

    @Test
    @DisplayName("주문 목록 상세내역 테스트")
    @Rollback(value = false)
    public void orderViewTest2() {
        //given
        Member member1 = memberService.findMember(1L);
        Member member2 = memberService.findMember(2L);

        Item item1 = Item.createItem("신발", "나이키 신발", 30000, 300);
        Item item2 = Item.createItem("자켓", "프라다 자켓", 100000, 50);
        Item item3 = Item.createItem("모자", "나이키 모자", 25000, 200);
        itemRepository.save(item1);
        itemRepository.save(item2);
        itemRepository.save(item3);

        /**
         * member1 : 신발 10개, 모자 5개 장바구니 담아서 주문
         * member2 : 자켓 7개 바로 주문
         */
        CartOrderDto cartOrderDto = new CartOrderDto();
        List<CartForm> cartOrderDtoList = new ArrayList<>();
        cartOrderDto.setCartOrderDtoList(cartOrderDtoList);

        CartForm cartForm1 = new CartForm();
        cartForm1.setItemId(item1.getId());
        cartForm1.setCount(10);

        CartForm cartForm2 = new CartForm();
        cartForm2.setItemId(item3.getId());
        cartForm2.setCount(5);

        cartOrderDtoList.add(cartForm1);
        cartOrderDtoList.add(cartForm2);

        orderService.order(member2.getId(), item2.getId(), 3);
        orderService.orders(member1.getId(), cartOrderDto);
        orderService.order(member1.getId(), item2.getId(), 1);

        //when
        List<OrderDto> result = orderRepository.findOrderDetail(member1.getId());


        //then
        assertThat(result.size()).isEqualTo(2);  //member1은 한 개의 주문을 헀다.
        assertThat(result.get(0).getOrderItemDtoList().size()).isEqualTo(3); //member1의 주문 상품은 두 개이다.
        assertThat(result.get(0).getOrderItemDtoList().get(1).getItemName()).isEqualTo("모자");

    }
}