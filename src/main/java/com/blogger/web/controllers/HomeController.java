package com.blogger.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/")
    public @ResponseBody String ShowWelcome(){
        return "Welcome to Pentax!!!";
    }
}
