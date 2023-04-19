package com.toyproject.ecommerce.controller;

import com.toyproject.ecommerce.domain.Member;
import com.toyproject.ecommerce.repository.query.CartQueryDto;
import com.toyproject.ecommerce.service.CartService;
import com.toyproject.ecommerce.service.ItemImageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    private final ItemImageService itemImageService;

    //장바구니 조회
    @GetMapping("/cart")
    public String cartView(Model model, HttpServletRequest request) {

        //세션에 저장되어있는 회원정보 가져오기
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<CartQueryDto> cartItemListForm = cartService.findCartItems(member.getId());

        model.addAttribute("cartItemForm", cartItemListForm);

    }

    //장바구니 담기
    @PostMapping("/cart")
    public String addCart() {

    }

}
