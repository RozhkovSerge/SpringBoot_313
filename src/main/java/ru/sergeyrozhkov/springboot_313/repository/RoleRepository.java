package ru.sergeyrozhkov.springboot_313.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sergeyrozhkov.springboot_313.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
