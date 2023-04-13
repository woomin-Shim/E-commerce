package com.toyproject.ecommerce.controller;


import com.toyproject.ecommerce.domain.Item;
import com.toyproject.ecommerce.domain.Member;
import com.toyproject.ecommerce.service.FileHandler;
import com.toyproject.ecommerce.service.ItemImageService;
import com.toyproject.ecommerce.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final FileHandler fileHandler;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        List<Item> items = itemService.findItems();
//        List<ItemImage> itemImages = itemImageService.findAllByDeleteYN("N");
        //queryDSL TODO

//        //엔티티 -> DTO
//        List<ItemListDto> itemListDto = items.stream()
//                .map(ItemListDto::new)
//                .collect(Collectors.toList());
        model.addAttribute("items", items);

        HttpSession session = request.getSession(false);

        //비로그인 사용자
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("home controller");
            return "home";
        }

        //로그인된 사용자
        log.info("userHome Controller");
        return "userHome";


    }

//    @GetMapping("/userHome")
//    public String userHome(Model model) {
//        List<Item> items = itemService.findItems();
//
//        model.addAttribute("items", items);
//        log.info("userHome Controller");
//        return "userHome";
//    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource showImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileHandler.getFullPath(filename));
    }

}
