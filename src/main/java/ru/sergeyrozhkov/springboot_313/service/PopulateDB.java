package ru.sergeyrozhkov.springboot_313.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sergeyrozhkov.springboot_313.model.Role;
import ru.sergeyrozhkov.springboot_313.model.User;
import ru.sergeyrozhkov.springboot_313.repository.RoleRepository;
import ru.sergeyrozhkov.springboot_313.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class PopulateDB {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public PopulateDB(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    protected void populateDB() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleRepository.saveAll(List.of(roleAdmin, roleUser));

        User admin = new User();
        admin.setFirstname("Bob");
        admin.setLastname("Johnson");
        admin.setEmail("bob@mail.com");
        admin.setAge((byte) 25);
        admin.setPassword("$2a$12$q98.zeMhcdtMh6.EIN/1eO9eJ5RbeU8G1Zl2Bo5zDMUulqYGjH3Pa"); //100
        admin.getRoles().add(roleAdmin);
        userRepository.save(admin);
        admin.getRoles().add(roleUser);
        userRepository.save(admin);

        User user = new User();
        user.setFirstname("Ivan");
        user.setLastname("Petrov");
        user.setEmail("ivan@mail.com");
        user.setAge((byte) 30);
        user.setPassword("$2a$12$q98.zeMhcdtMh6.EIN/1eO9eJ5RbeU8G1Zl2Bo5zDMUulqYGjH3Pa"); //100
        user.getRoles().add(roleUser);
        userRepository.save(user);
    }
}