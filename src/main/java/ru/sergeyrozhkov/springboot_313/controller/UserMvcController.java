package ru.sergeyrozhkov.springboot_313.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sergeyrozhkov.springboot_313.model.Role;
import ru.sergeyrozhkov.springboot_313.model.User;
import ru.sergeyrozhkov.springboot_313.service.UserService;

import java.security.Principal;
import java.util.Map;

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
        if(principal instanceof OAuth2AuthenticationToken) {
            Map<String, Object> oAuth2UserDetails = ((OAuth2AuthenticationToken) principal).getPrincipal().getAttributes();
            User user = userService.findUserByEmail((String) oAuth2UserDetails.get("email"));
            if(user == null) {
                user = new User();
                user.setId(0L);
                user.setFirstname((String) oAuth2UserDetails.get("name"));
                user.setLastname("n/a");
                user.setAge((byte)0);
                user.setEmail((String) oAuth2UserDetails.get("email"));
                user.getRoles().add(new Role("ROLE_USER"));
            }
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", userService.findUserByEmail(principal.getName()));
        }

        return "user";
    }
}
