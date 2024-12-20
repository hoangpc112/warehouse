package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.User;
import com.nttemoi.warehouse.repositories.UserRepository;
import com.nttemoi.warehouse.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List <User> findAll () {
        return userRepository.findAll();
    }

    @Override
    public User findById (Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername (String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void save (User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteById (Long id) {
        userRepository.deleteById(id);
    }
}
