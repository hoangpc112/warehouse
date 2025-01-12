package com.nttemoi.warehouse.repositories;

import com.nttemoi.warehouse.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    Optional <User> findByUsername (String username);

    Page <User> findByUsernameLike (String username, Pageable pageable);

    @Query ("UPDATE User t SET t.active = :status WHERE t.id = :id")
    @Modifying
    void updateUserStatus (Long id, boolean status);
}
