package com.toyproject.ecommerce.service;

import com.toyproject.ecommerce.entity.Item;
import com.toyproject.ecommerce.entity.ItemImage;
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
    private final ItemImageService itemImageService;

    //상품 정보 저장
    public Long saveItem(ItemServiceDTO itemServiceDTO, List<MultipartFile> multipartFileList) throws IOException {
        Item item = Item.createItem(itemServiceDTO.getName(),
                itemServiceDTO.getDescription(),
                itemServiceDTO.getPrice(),
                itemServiceDTO.getStockQuantity());

        List<ItemImage> itemImages = filehandler.storeImages(multipartFileList);

        //대표 상품 이미지 등록
        itemImages.get(0).isFirstImage("Y");

        for (ItemImage itemImage : itemImages) {
            item.addItemImage(itemImageRepository.save(itemImage));
        }

        return itemRepository.save(item).getId();
    }

    //상품 정보 업데이트 (Dirty Checking, 변경감지)
    public void updateItem(ItemServiceDTO itemServiceDTO,  List<MultipartFile> multipartFileList) throws IOException {

        Item findItem = itemRepository.findById(itemServiceDTO.getId()).orElse(null);  //DB에서 찾아옴 -> 영속 상태

        findItem.updateItem(itemServiceDTO.getName(), itemServiceDTO.getDescription(), itemServiceDTO.getPrice(), itemServiceDTO.getStockQuantity());

        //상품 이미지를 수정(삭제, 추가) 하지 않으면 실행 x
        if(!multipartFileList.get(0).isEmpty()) {
            itemImageService.addItemImage(multipartFileList, findItem);
        }

        List<ItemImage> itemImageList = itemImageRepository.findByItemIdAndDeleteYN(itemServiceDTO.getId(), "N");
        itemImageList.get(0).isFirstImage("Y");
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
