package com.toyproject.ecommerce.service;

import com.toyproject.ecommerce.domain.Item;
import com.toyproject.ecommerce.domain.ItemImage;
import com.toyproject.ecommerce.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    @Transactional
    public void saveItem(Item item, List<ItemImage> itemImages) {
        for (ItemImage itemImage : itemImages) {
            item.addItemImage(itemImage);
        }
        itemRepository.save(item);
    }
}
