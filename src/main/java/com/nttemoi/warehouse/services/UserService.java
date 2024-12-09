package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.User;

import java.util.List;

public interface UserService {
    List <User> findAll ();

    User findById (Long id);

    User findByUsername (String username);

    void save (User user);

    void deleteById (Long id);
}
