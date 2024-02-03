package com.training.tinelli.sales.rest.controller;

import com.training.tinelli.sales.JwtService;
import com.training.tinelli.sales.domain.entity.User;
import com.training.tinelli.sales.exception.InvalidPasswordException;
import com.training.tinelli.sales.rest.dto.CredentialsDTO;
import com.training.tinelli.sales.rest.dto.TokenDTO;
import com.training.tinelli.sales.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v0/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody @Valid User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.saveUser(user);
    }

    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody CredentialsDTO credentials) {
        try {
            User user = User.builder().username(credentials.getUsername()).password(credentials.getPassword()).build();
            UserDetails userDetails = userService.authenticate(user);
            String token = jwtService.generateToken(user);

            return new TokenDTO(userDetails.getUsername(), token);
        } catch (UsernameNotFoundException | InvalidPasswordException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }
}
