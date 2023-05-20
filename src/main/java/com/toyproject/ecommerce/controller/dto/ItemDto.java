package com.toyproject.ecommerce.controller.dto;

import com.toyproject.ecommerce.entity.Item;
import lombok.Getter;
import lombok.Setter;

//상품 목록 조회 뷰에 쓰일 Dto
@Getter
@Setter
public class ItemDto {

    private Long id;
    private String name;
    private String description;
    private int price;
    private int stockQuantity;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
    }
}
