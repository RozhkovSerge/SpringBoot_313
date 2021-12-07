package ru.sergeyrozhkov.springboot_313.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sergeyrozhkov.springboot_313.model.User;
import ru.sergeyrozhkov.springboot_313.service.RoleService;
import ru.sergeyrozhkov.springboot_313.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/getPrincipal")
    public User getPrincipal(Principal principal) {

        return findUserByEmail(principal.getName());
    }

    @GetMapping("/users")
    public List<User> getAll() {

        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {

        return userService.findUserById(id);
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        userService.save(user);
    }

    @PutMapping("/users")
    public void editUser(@RequestBody User user) {
        userService.save(user);
    }

    public User findUserByEmail(String email) {

        return userService.findUserByEmail(email);
    }



}
