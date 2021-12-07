package ru.sergeyrozhkov.springboot_313.service;

import ru.sergeyrozhkov.springboot_313.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
}
