package com.toyproject.ecommerce.service;

import com.toyproject.ecommerce.domain.Item;
import com.toyproject.ecommerce.domain.ItemImage;
import com.toyproject.ecommerce.repository.ItemImageRepository;
import com.toyproject.ecommerce.repository.ItemRepository;
import com.toyproject.ecommerce.service.dto.ItemServiceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final FileHandler filehandler;

    //상품 정보 저장
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

    //상품 정보 업데이트
    public void updateItem(ItemServiceDTO itemServiceDTO) throws IOException {

        Item findItem = itemRepository.findById(itemServiceDTO.getId()).orElse(null);  //DB에서 찾아옴 -> 영속 상태

        log.info("=====findItem={}", findItem.getName());

        log.info("======itemServiceDTO.getName={}", itemServiceDTO.getName());

        //Dirty Checking
        findItem.updateItem(itemServiceDTO.getName(), itemServiceDTO.getDescription(), itemServiceDTO.getPrice(), itemServiceDTO.getStockQuantity());

        log.info("=====findItem={}", findItem.getName());
    }


    @Transactional(readOnly=true)
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    @Transactional(readOnly=true)
    public Item findItem(Long ItemId) {
        return itemRepository.findById(ItemId).orElse(null);
    }
}
