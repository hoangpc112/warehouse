package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<User> findAll();

    Page<User> findAll(int page, int size);

    User findById(Long id);

    User findByUsername(String username);

    Page<User> findByUsername(String keyword, int page, int size);

    Page<User> findAllAndSort(int page, int size, String order, String orderBy);

    Page<User> findByKeywordAndSort(String keyword, int page, int size, String order, String orderBy);

    void save(User user);

    void updateUserStatus(Long id, boolean status);

    //  void deleteById(Long id);

    void registerUser(User user);

}
