package com.toyproject.ecommerce.controller;

import com.toyproject.ecommerce.controller.dto.ItemDTO;
import com.toyproject.ecommerce.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createItemForm() {
        return "item/itemForm";
    }

    @PostMapping("/items/new")
    public String createItem(@ModelAttribute ItemDTO itemDTO) throws IOException {

        log.info("multipartFiles={}", itemDTO.getItemImages());
        itemService.saveItem(itemDTO.toServiceDTO());


        return "redirect:/userHome";
    }

    /**
     * 컨트롤러와 서비스간 통신을 할 때, 컨트롤러가 뷰와 통신할 때 사용한 DTO를 그대로 사용하면
     * 강한 의존이 생겨 위험!!
     */
}
