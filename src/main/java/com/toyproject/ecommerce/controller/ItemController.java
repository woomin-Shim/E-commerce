package com.toyproject.ecommerce.controller;

import com.toyproject.ecommerce.controller.dto.ItemForm;
import com.toyproject.ecommerce.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

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

}
