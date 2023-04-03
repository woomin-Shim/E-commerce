package com.toyproject.ecommerce.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }

    @RequestMapping("/userHome")
    public String userHome() {
        log.info("userHome Controller");
        return "userHome";
    }

//    @RequestMapping("/main")  //로그인된 사용자 메인 화면
//    public String userHome() {
//
//    }
}
