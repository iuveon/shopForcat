package com.forcat.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping(value = "/") // http://localhost/*
    public String main() {
        return "main";
    }
}
