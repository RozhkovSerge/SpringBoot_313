package ru.sergeyrozhkov.springboot_313.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import ru.sergeyrozhkov.springboot_313.model.CustomOauth2User;
import ru.sergeyrozhkov.springboot_313.service.CustomOauth2UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Oauth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();
        super.onAuthenticationSuccess(request, response, chain, authentication);
    }
}
