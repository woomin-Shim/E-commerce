package com.toyproject.ecommerce.entity;

import com.toyproject.ecommerce.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;
    private String description;
    private int price;
    private int stockQuantity;

    @OneToMany(mappedBy = "item")
    private List<ItemImage> itemImageList = new ArrayList<>();

    //== 연관 관계 편의 메소드 ==//
    public void addItemImage(ItemImage itemImage) {
        itemImageList.add(itemImage);
        itemImage.changeItem(this);
    }

    @Builder
    private Item(String name, String description, int price, int stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public static Item createItem(String name, String description, int price, int stockQuantity) {
        return new Item(name, description, price, stockQuantity);
    }

    public void updateItem(String name, String description, int price, int stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }


    //== 비즈니스 메서드 ==//

    //주문시 상품 재고 감소
    public void minStock(int quantity) {
        int restQuantity = stockQuantity - quantity;
        if (restQuantity < 0) {
            throw new NotEnoughStockException("상품 재고가 부족합니다!!");
        }
        stockQuantity = restQuantity;
    }

    //주문 취소시 상품 재고 증가
    public void addStock(int quantity) {
        stockQuantity += quantity;
    }

}
