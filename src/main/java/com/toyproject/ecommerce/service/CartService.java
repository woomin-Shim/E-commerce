package com.toyproject.ecommerce.service;

import com.toyproject.ecommerce.domain.Cart;
import com.toyproject.ecommerce.domain.CartItem;
import com.toyproject.ecommerce.domain.Item;
import com.toyproject.ecommerce.domain.Member;
import com.toyproject.ecommerce.repository.CartItemRepository;
import com.toyproject.ecommerce.repository.CartRepository;
import com.toyproject.ecommerce.repository.ItemRepository;
import com.toyproject.ecommerce.repository.MemberRepository;
import com.toyproject.ecommerce.repository.query.CartQueryDto;
import com.toyproject.ecommerce.repository.query.CartQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartQueryRepository cartQueryRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public Cart findCart(Long memberId) {
        return cartRepository.findByMemberId(memberId).orElse(null);
    }

    @Transactional(readOnly = true)
    public CartItem findCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElse(null);
    }

    /**
     * 장바구니 담기(추가)
     */
    public Long addCart(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Cart cart = cartRepository.findByMemberId(memberId).orElse(null);
        Item item = itemRepository.findById(itemId).get();

        //장바구니 없으면 생성
        if (cart == null) {
            log.info("장바구니 신규 생성");
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        //장바구니안에 장바구니 상품 조회
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId()).orElse(null);


        //장바구니 상품이 없으면 생성
        if (cartItem == null) {
            cartItem = CartItem.createCartItem(count, cart, item);
            CartItem savedCartItem = cartItemRepository.save(cartItem);
            log.info("cartItemId={}", cartItem.getId());
            return savedCartItem.getId();
        }

        //장바구니 상품이 존재하면 수량 변경 (Dirty checking)
        cartItem.changeCount(count);
        return cartItem.getId();

    }

    /**
     * 장바구니 조회
     */
    @Transactional(readOnly = true)
    public List<CartQueryDto> findCartItems(Long memberId) {
        Cart cart = cartRepository.findByMemberId(memberId).orElse(null);
        List<CartQueryDto> cartQueryDtos = cartQueryRepository.findCartQueryDtos(cart.getId());
        return cartQueryDtos;
    }

    /**
     * 장바구니 삭제
     */
    public void deleteCartItem(Long itemId) {
        CartItem findCartItem = cartItemRepository.findById(itemId).orElse(null);
        cartItemRepository.delete(findCartItem);
    }

}
