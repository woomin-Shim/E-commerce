package com.toyproject.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    @GetMapping("/items/new")
    public String createItemForm() {
        return "item/itemForm";
    }
}
