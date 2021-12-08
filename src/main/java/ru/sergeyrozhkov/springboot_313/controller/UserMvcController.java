package ru.sergeyrozhkov.springboot_313.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sergeyrozhkov.springboot_313.service.UserService;

import java.security.Principal;

@Controller
public class UserMvcController {
    private UserService userService;

    @Autowired
    public UserMvcController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getIndex() {
        return "index";
    }

    @GetMapping("/user")
    public String showUserPage(Principal principal, Model model) {
        model.addAttribute(userService.findUserByEmail(principal.getName()));
        return "user";
    }
}
