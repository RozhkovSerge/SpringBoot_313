package ru.sergeyrozhkov.springboot_313.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserMvcController {

    @GetMapping
    public String getIndex() {
        return "index";
    }
}
