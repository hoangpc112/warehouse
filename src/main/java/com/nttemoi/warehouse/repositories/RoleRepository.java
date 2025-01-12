package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository <Role, Long> {
    Role findByName (String name);

    List <Role> findByNameContaining (String name);
}