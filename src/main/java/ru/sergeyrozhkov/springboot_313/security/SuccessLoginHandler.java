package ru.sergeyrozhkov.springboot_313.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.sergeyrozhkov.springboot_313.model.CustomOauth2User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessLoginHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();
//        System.out.println(oauth2User.getEmail());
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/user");
        }

    }
}
