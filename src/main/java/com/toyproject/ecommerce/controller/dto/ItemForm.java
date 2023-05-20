package com.toyproject.ecommerce.controller.dto;

import com.toyproject.ecommerce.service.dto.ItemServiceDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemForm {


    private Long itemId;
    @NotEmpty(message = "상품 이름은 필수입니다.")
    private String name;  //상품명
    @NotNull(message = "상품 가격은 필수입니다.")
    private int price; //상품 가격
    @NotNull(message = "상품 재고 수량은 필수입니다.")
    private int stockQuantity;  //재고 수량
    private String description;  //상품 설명

    //상품 수정, 장바구니,상품 상세에 사용
    private List<ItemImageDto> itemImageListDto = new ArrayList<>();

    public ItemServiceDTO toServiceDTO() {
        return ItemServiceDTO.builder()
                .id(itemId)
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .description(description)
                .build();
    }
}
