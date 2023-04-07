package com.toyproject.ecommerce.controller.dto;

import com.toyproject.ecommerce.domain.Item;
import com.toyproject.ecommerce.domain.ItemImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ItemImageDto {

    private Long id;
    private String originalName;
    private String storeName;
    private String deleteYN;
    private Item item;

    public ItemImageDto(ItemImage itemImage) {
        this.id = itemImage.getId();
        this.originalName = itemImage.getOriginalName();
        this.storeName = itemImage.getStoreName();
        this.deleteYN = itemImage.getDeleteYN();
        this.item = itemImage.getItem();
    }
}
