package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.Role;
import com.nttemoi.warehouse.entities.User;
import com.nttemoi.warehouse.repositories.RoleRepository;
import com.nttemoi.warehouse.repositories.UserRepository;
import com.nttemoi.warehouse.services.RoleService;
import com.nttemoi.warehouse.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, RoleService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by("username")));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Page<User> findByUsername(String keyword, int page, int size) {
        return userRepository.findByUsernameLike(keyword, PageRequest.of(page, size, Sort.by("username")));
    }

    @Override
    public Page<User> findAllAndSort(int page, int size, String order, String orderBy) {
        if (order.equals("asc")) {

            return userRepository.findAll(PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        } else {
            return userRepository.findAll(PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }
    }

    @Override
    public Page<User> findByKeywordAndSort(String keyword, int page, int size, String order, String orderBy) {
        if (order.equals("asc")) {
            return userRepository.findByUsernameLike(keyword, PageRequest.of(page, size, Sort.by(orderBy).ascending()));
        } else {
            return userRepository.findByUsernameLike(keyword, PageRequest.of(page, size, Sort.by(orderBy).descending()));
        }

    }


    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUserStatus(Long id, boolean status) {
        userRepository.updateUserStatus(id, status);
    }


    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void registerUser(User user) {
        Role userRole = roleRepository.findByName("USER");
        user.addRole(userRole);
        userRepository.save(user);
    }


    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
