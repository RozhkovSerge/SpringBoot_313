package ru.sergeyrozhkov.springboot_313.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sergeyrozhkov.springboot_313.model.Role;
import ru.sergeyrozhkov.springboot_313.repository.RoleRepository;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
