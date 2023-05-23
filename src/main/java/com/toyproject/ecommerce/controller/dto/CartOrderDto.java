package com.toyproject.ecommerce.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// 장바구니 상품들 주문 DTO
@Getter
@Setter
public class CartOrderDto {

    List<CartForm> cartOrderDtoList;

    public CartOrderServiceDto toServiceDto() {
        return CartOrderServiceDto.builder()
                .cartOrderDtoList(cartOrderDtoList)
                .build();
    }

}
