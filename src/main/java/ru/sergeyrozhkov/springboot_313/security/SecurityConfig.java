package ru.sergeyrozhkov.springboot_313.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.sergeyrozhkov.springboot_313.service.CustomOauth2UserService;
import ru.sergeyrozhkov.springboot_313.service.UserService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final CustomOauth2UserService customOauth2UserService;
    private final SuccessLoginHandler successLoginHandler;

    @Autowired
    public SecurityConfig(UserService userService, CustomOauth2UserService customOauth2UserService, SuccessLoginHandler successLoginHandler) {
        this.userService = userService;
        this.customOauth2UserService = customOauth2UserService;
        this.successLoginHandler = successLoginHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .usernameParameter("email")
                .successHandler(successLoginHandler)

                .and()
                .oauth2Login().successHandler(successLoginHandler)
                .loginPage("/login").userInfoEndpoint().userService(customOauth2UserService);

        http.csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
