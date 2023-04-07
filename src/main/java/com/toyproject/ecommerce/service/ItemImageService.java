package com.toyproject.ecommerce.service;

import com.toyproject.ecommerce.domain.ItemImage;
import com.toyproject.ecommerce.repository.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;


    //삭제 여부를 판단하여 상품 이미지 정보를 조회한다
    @Transactional(readOnly = true)
    public List<ItemImage> findItemDetail(Long id, String YN) {
        return itemImageRepository.findByItemIdAndDeleteYN(id, YN);
    }

    @Transactional(readOnly = true)
    public List<ItemImage> findAllByDeleteYN(String YN) {
        return itemImageRepository.findAllByDeleteYN(YN);
    }



    public void delete(Long itemImageId) {
        ItemImage itemImage = itemImageRepository.findById(itemImageId).get();
        itemImage.deleteSet("Y");
    }
}
