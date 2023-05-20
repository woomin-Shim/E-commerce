package com.toyproject.ecommerce.service;

import com.toyproject.ecommerce.entity.Item;
import com.toyproject.ecommerce.repository.ItemRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    private Item createItem() {
        return Item.createItem("후드 집업", "데상트 후드 집업", 70000, 300);
    }

    @Test
    @DisplayName("상품 수정 테스트")
    public void updateItem() {
        //given
        Item item = this.createItem();
        itemRepository.save(item);

        //when
        Item findItem = itemRepository.findById(item.getId()).orElse(null);
        findItem.updateItem("후드 집업", "지프 후드 집업", 55000, 300);

        //then
        Assert.assertEquals(item.getName(), "후드 집업");
        Assert.assertEquals(item.getPrice(), 55000);

    }
}
