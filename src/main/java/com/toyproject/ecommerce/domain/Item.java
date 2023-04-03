package com.toyproject.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private List<ItemImage> itemImageList;

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

}
