package com.toyproject.ecommerce.controller;

import com.toyproject.ecommerce.controller.dto.ItemForm;
import com.toyproject.ecommerce.controller.dto.ItemImageDto;
import com.toyproject.ecommerce.controller.dto.ItemDto;
import com.toyproject.ecommerce.domain.Item;
import com.toyproject.ecommerce.domain.ItemImage;
import com.toyproject.ecommerce.service.ItemImageService;
import com.toyproject.ecommerce.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemImageService itemImageService;

    /**
     * 상품 등록
     */
    @GetMapping("/items/new")
    public String createItemForm(Model model) {
        model.addAttribute("itemForm", new ItemForm());
        return "item/itemForm";
    }

    /**
     * 컨트롤러와 서비스간 통신을 할 때, 컨트롤러가 뷰와 통신할 때 사용한 DTO를 그대로 사용하면
     * 강한 의존이 생겨 위험!!
     */

    @PostMapping("/items/new")
    public String createItem(@Valid @ModelAttribute ItemForm itemForm, BindingResult bindingResult, Model model,
                             @RequestPart(name = "itemImages") List<MultipartFile> multipartFiles
    ) throws IOException {

        if (bindingResult.hasErrors()) return "item/itemForm";

        //상품 이미지를 등록안하면
        if (multipartFiles.get(0).isEmpty()) {
            model.addAttribute("errorMessage", "상품 사진을 등록해주세요!");
            return "item/itemForm";
        }

        itemService.saveItem(itemForm.toServiceDTO(), multipartFiles);

        return "redirect:/userHome";
    }


    /**
     * 상품 수정
     */
    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable(name = "itemId") Long itemId, Model model) {

        Item findItem = itemService.findItem(itemId);
        List<ItemImage> itemImageList = itemImageService.findItemImageDetail(itemId, "N");

        //엔티티 -> DTO로 변환
        List<ItemImageDto> itemImageListDto = itemImageList.stream()
                .map(ItemImageDto::new)
                .collect(Collectors.toList());


        ItemForm itemForm = new ItemForm(
                findItem.getId(),
                findItem.getName(),
                findItem.getPrice(),
                findItem.getStockQuantity(),
                findItem.getDescription(),
                itemImageListDto
        );

        model.addAttribute("itemForm", itemForm);

        return "item/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute ItemForm itemForm, @RequestPart(name = "itemImages") List<MultipartFile> multipartFiles,
                             Model model) throws IOException {

        //상품 이미지를 등록안하면
        if (multipartFiles.get(0).isEmpty()) {
            model.addAttribute("errorMessage", "상품 사진을 등록해주세요!");
            return "item/itemForm";
        }

        //상품 정보 수정
        itemService.updateItem(itemForm.toServiceDTO(), multipartFiles);

        return "redirect:/userHome";
    }

    /**
     * 상품 목록 조회
     */
    @GetMapping("/items")
    public String items(Model model) {

        List<Item> itemList = itemService.findItems();

        //엔티티 -> DTO
        List<ItemDto> itemListDto = itemList.stream()
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());

        model.addAttribute("itemList", itemListDto);

        return "item/itemList";
    }

    /**
     * 아이템 이미지 삭제 처리
     */
    @PostMapping("item/delete")
    public String deleteItemImage(@RequestParam("itemImageId") Long itemImageId, @RequestParam("itemId") Long itemId) {

        itemImageService.delete(itemImageId);

        return "redirect:/items/ " + itemId + "/edit";
    }
}
