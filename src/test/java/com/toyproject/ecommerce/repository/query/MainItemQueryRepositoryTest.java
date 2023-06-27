package com.toyproject.ecommerce.repository.query;


import com.toyproject.ecommerce.controller.dto.ItemSearchCondition;
import com.toyproject.ecommerce.entity.Item;
import com.toyproject.ecommerce.entity.ItemImage;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MainItemQueryRepositoryTest {

    @Autowired
    MainItemQueryRepository mainRepository;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    @DisplayName("메인 페이지 페이징 테스트")
    public void mainPageTest() {

        //given
        Item item1 = Item.createItem("나이키 신발", "나이키 신발", 30000, 300);
        Item item2 = Item.createItem("프라다 자켓", "프라다 자켓", 100000, 50);
        em.persist(item1);
        em.persist(item2);

        ItemImage shoesImage1 = ItemImage.builder()
                .originalName("shoes.jpeg")
                .storeName("shoes11.jpeg")
                .build();

        ItemImage shoesImage2 = ItemImage.builder()
                .originalName("shoes2.jpeg")
                .storeName("shoes22.jpeg")
                .build();

        shoesImage1.isFirstImage("Y");  //대표 상품 이미지 설정

        em.persist(shoesImage2);
        em.persist(shoesImage1);

        item1.addItemImage(shoesImage1);
        item1.addItemImage(shoesImage2);


        ItemImage jacketImage1 = ItemImage.builder()
                .originalName("jacket.jpeg")
                .storeName("jacket11.jpeg")
                .build();

        ItemImage jacketImage2 = ItemImage.builder()
                .originalName("jacket2.jpeg")
                .storeName("jacket22.jpeg")
                .build();

        jacketImage2.isFirstImage("Y");

        em.persist(jacketImage1);
        em.persist(jacketImage2);

        item2.addItemImage(jacketImage1);
        item2.addItemImage(jacketImage2);


        //when
        Pageable pageable = PageRequest.of(0, 2);
        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setItemName("나이키");

        Page<MainItemDto> result = mainRepository.findMainItem(pageable, condition);
        List<MainItemDto> content = result.getContent();

        //then
        assertThat(content.size()).isEqualTo(2);  //content의 사이즈는 2이다.
        assertThat(content).extracting("itemName").containsExactly("나이키 신발", "프라다 자켓");
        assertThat(content).extracting("imgUrl").containsExactly("shoes11.jpeg", "jacket22.jpeg");

        /**
         * 검색조건(ItemName) -> 나이키
         */
//        assertThat(content.size()).isEqualTo(1);
//        assertThat(content.get(0).getItemName()).isEqualTo("나이키 신발");

        System.out.println(result.getTotalPages());  //전체 페이지 수
        System.out.println(result.getTotalElements());  //전체 데이터 수
        System.out.println(result.getNumber());  //현재 페이시
        System.out.println(result.getSize());  //페이지 크기
    }
}