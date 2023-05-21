package com.toyproject.ecommerce.controller;

import com.toyproject.ecommerce.controller.dto.CartForm;
import com.toyproject.ecommerce.entity.Member;
import com.toyproject.ecommerce.exception.NotEnoughStockException;
import com.toyproject.ecommerce.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 단일 상품 바로 주문
     */
    @PostMapping("/order")
    public ResponseEntity<String> order(@ModelAttribute CartForm cartForm, HttpServletRequest request) {

        //CartController 에 작성해둔 세션 정보 조회하는 기능 공용으로 사용
        Member member = CartController.getMember(request);

        if (member == null) {
            return new ResponseEntity<String>("로그인이 필요한 서비스입니다.", HttpStatus.UNAUTHORIZED);
        }

        try {
            orderService.order(member.getId(), cartForm.getItemId(), cartForm.getCount());
        } catch (NotEnoughStockException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("order Success");
    }
}
