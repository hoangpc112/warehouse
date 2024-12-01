package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    User findByUsername (String username);
}
