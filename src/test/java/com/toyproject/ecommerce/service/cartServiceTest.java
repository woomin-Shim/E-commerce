package com.toyproject.ecommerce.service;

import com.toyproject.ecommerce.entity.CartItem;
import com.toyproject.ecommerce.entity.Item;
import com.toyproject.ecommerce.entity.Member;
import com.toyproject.ecommerce.repository.CartItemRepository;
import com.toyproject.ecommerce.repository.ItemRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class cartServiceTest {

    @Autowired
    CartService cartService;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    MemberService memberService;


    @Test
    @DisplayName("장바구니 담기 테스트")
    @Rollback(value = false)
    public void addCart() {
        //given
        Member member = memberService.findMember(1L);
        Item item = createItem();
        
        //when
        Long cartItemId = cartService.addCart(member.getId(), item.getId(), 1);

        //then
        assertEquals(item, cartItemRepository.findById(cartItemId).get().getItem());

    }

    @Test
    @DisplayName("장바구니 담기 테스트(장바구니 상품이 존재할 경우)")
    public void addCartV2() {
        //given
        Member member = memberService.findMember(1L);
        Item item = createItem();
        cartService.addCart(member.getId(), item.getId(), 1);

        //when
        Long cartItemId = cartService.addCart(member.getId(), item.getId(), 4);

        //then
        CartItem cartItem = cartItemRepository.findById(cartItemId).get();
        assertEquals("장바구니 상품 수량은 4개여야 한다.", 4, cartItem.getCount());
        assertEquals(item.getId(), cartItem.getItem().getId());
    }

    private Item createItem() {

        Item item = Item.createItem("신발", "나이키 신발", 3000, 100);
        return itemRepository.save(item);
    }

}
