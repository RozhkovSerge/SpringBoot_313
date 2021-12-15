package ru.sergeyrozhkov.springboot_313.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.sergeyrozhkov.springboot_313.model.CustomOauth2User;
import ru.sergeyrozhkov.springboot_313.model.User;
import ru.sergeyrozhkov.springboot_313.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Oauth2UserSuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;

    @Autowired
    public Oauth2UserSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();
        User user = userService.findUserByEmail(oauth2User.getEmail());
        if (user == null) {
            response.sendRedirect("/user");
        } else {
            Set<String> roles = user.getRoles().stream().map(s -> s.toString()).collect(Collectors.toSet());
            if (roles.contains("ADMIN")) {
                response.sendRedirect("/");
            } else {
                response.sendRedirect("/user");
            }
        }
    }
}
