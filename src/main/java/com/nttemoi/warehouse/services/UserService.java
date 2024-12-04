package com.nttemoi.warehouse.services;

import com.nttemoi.warehouse.entities.User;
import com.nttemoi.warehouse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        User user = findByUsername(username);

        if (user != null) {
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).build();
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }

    public List <User> findAll () {
        return userRepository.findAll();
    }

    public User findById (long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername (String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void save (User user) {
        userRepository.save(user);
    }

    public void deleteById (Long id) {
        userRepository.deleteById(id);
    }
}
