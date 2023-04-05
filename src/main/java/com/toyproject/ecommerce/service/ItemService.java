package com.toyproject.ecommerce.service;

import com.toyproject.ecommerce.domain.Item;
import com.toyproject.ecommerce.domain.ItemImage;
import com.toyproject.ecommerce.repository.ItemImageRepository;
import com.toyproject.ecommerce.repository.ItemRepository;
import com.toyproject.ecommerce.service.dto.ItemServiceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final FileHandler filehandler;

    public Long saveItem(ItemServiceDTO itemServiceDTO, List<MultipartFile> multipartFileList) throws IOException {
        Item item = Item.createItem(itemServiceDTO.getName(),
                itemServiceDTO.getDescription(),
                itemServiceDTO.getPrice(),
                itemServiceDTO.getStockQuantity());

        List<ItemImage> itemImages = filehandler.storeImages(multipartFileList);


        for (ItemImage itemImage : itemImages) {
            item.addItemImage(itemImageRepository.save(itemImage));
        }

        return itemRepository.save(item).getId();
    }
}
