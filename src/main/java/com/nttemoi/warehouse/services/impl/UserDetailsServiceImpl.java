package com.nttemoi.warehouse.services.impl;

import com.nttemoi.warehouse.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceImpl userService;

    public UserDetailsServiceImpl (UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user != null) {
            return org.springframework.security.core.userdetails.User.withUsername(user.getUsername()).password(user.getPassword()).build();
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }
}
