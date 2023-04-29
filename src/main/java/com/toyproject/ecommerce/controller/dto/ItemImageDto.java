package com.toyproject.ecommerce.controller.dto;


import com.toyproject.ecommerce.domain.ItemImage;
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


    public ItemImageDto(ItemImage itemImage) {
        this.id = itemImage.getId();
        this.originalName = itemImage.getOriginalName();
        this.storeName = itemImage.getStoreName();
        this.deleteYN = itemImage.getDeleteYN();
    }
}
