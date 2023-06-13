package com.toyproject.ecommerce.repository.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

/**
 * 메인 페이지 아이템 조회에 쓰일 DTO
 */
@Getter
@Setter
public class MainItemDto {

    private Long itemId;
    private String itemName;  //아이템명
    private int itemPrice;  //아이템 가격
    private String imgUrl;  //대표 상품 이미지

    @QueryProjection
    public MainItemDto(Long itemId, String itemName, int itemPrice, String imgUrl) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.imgUrl = imgUrl;
    }
}
