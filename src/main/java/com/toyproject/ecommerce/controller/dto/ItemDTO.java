package com.toyproject.ecommerce.controller.dto;

import com.toyproject.ecommerce.service.dto.ItemServiceDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ItemDTO {

    private String name;  //상품명
    private int price; //상품 가격
    private int stockQuantity;  //재고 수량
    private String description;  //상품 설명
    private List<MultipartFile> itemImages; //상품 사진들

    public ItemServiceDTO toServiceDTO() {
        return ItemServiceDTO.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .description(description)
                .itemImages(itemImages)
                .build();
    }
}
