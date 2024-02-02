package com.training.tinelli.sales.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!username.equals("enzo"))
            throw new UsernameNotFoundException("Usuário não encontrado.");

        return User.builder()
                .username("enzo")
                .password("123")
                .roles("USER", "ADMIN")
                .build();
    }
}
