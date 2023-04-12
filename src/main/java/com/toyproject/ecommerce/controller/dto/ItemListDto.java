package com.toyproject.ecommerce.controller.dto;

import com.toyproject.ecommerce.domain.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ItemListDto {

    private Long id;
    private String name;
    private String description;
    private int price;
    private int stockQuantity;

    private List<ItemImageDto> itemImageDtoList;

    public ItemListDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.itemImageDtoList = item.getItemImageList().stream()
                .map(ItemImageDto::new)
                .collect(Collectors.toList());
    }

}
