package com.training.tinelli.sales.service.impl;

import com.training.tinelli.sales.domain.repository.UserRepository;
import com.training.tinelli.sales.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import com.training.tinelli.sales.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        String[] roles = user.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return  org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }

    @Transactional
    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public UserDetails authenticate(User user) {
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        boolean isEqualsPassword = encoder.matches(user.getPassword(), userDetails.getPassword());

        if (!isEqualsPassword) throw new InvalidPasswordException();

        return userDetails;
    }
}
