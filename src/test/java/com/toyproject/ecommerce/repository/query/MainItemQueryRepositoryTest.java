package com.toyproject.ecommerce.repository.query;


import com.toyproject.ecommerce.entity.Item;
import com.toyproject.ecommerce.entity.ItemImage;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MainItemQueryRepositoryTest {

    @Autowired
    MainItemQueryRepository mainRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("메인 페이지 페이징 테스트")
    public void mainPageTest() {

        //given
        Item item1 = Item.createItem("신발", "나이키 신발", 30000, 300);
        Item item2 = Item.createItem("자켓", "프라다 자켓", 100000, 50);
        em.persist(item1);
        em.persist(item2);

        ItemImage shoesImage1 = ItemImage.builder()
                .originalName("shoes.jpeg")
                .storeName("shoes11.jpeg")
                .build();

        shoesImage1.isFirstImage("Y");  //대표 상품 이미지 설정

        ItemImage shoesImage2 = ItemImage.builder()
                .originalName("shoes2.jpeg")
                .storeName("shoes22.jpeg")
                .build();

        em.persist(shoesImage2);
        em.persist(shoesImage1);


        em.flush();
        em.clear();

        //when

        //then
    }
}