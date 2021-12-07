package ru.sergeyrozhkov.springboot_313.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.sergeyrozhkov.springboot_313.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    User findUserByEmail(String email);

    User findUserById(Long id);

    void save(User user);

    void delete(User user);
}
