package com.toyproject.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemImage {

    @Id
    @GeneratedValue
    @Column(name = "item_image_id")
    private Long id;
    private String originalName; //원본 파일명
//    private String originalPath; //원본 경로명
    private String storeName; //서버에 저장될 경로명
    private String deleteYN; //이미지 파일 삭제 여부
    private String firstImage;  //썸네일 이미지 설정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    private ItemImage(String originalName, String storeName, String deleteYN) {
        this.originalName = originalName;
        this.storeName = storeName;
        this.deleteYN = "N";
    }

    public void changeItem(Item item) {
        this.item = item;
    }

    public void isFirstImage(String yn) {
        this.firstImage = yn;
    }

    public void deleteSet(String deleteYN) {
        this.deleteYN = deleteYN;
    }

}
