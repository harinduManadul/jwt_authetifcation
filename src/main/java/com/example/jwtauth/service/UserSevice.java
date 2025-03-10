package com.example.jwtauth.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwtauth.model.User;
import com.example.jwtauth.repository.UserRepository;
import com.example.jwtauth.requestModel.LoginRequest;

@Service
public class UserSevice {

    private final UserRepository uRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSevice(UserRepository uRepository, PasswordEncoder passwordEncoder) {
        this.uRepository = uRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return uRepository.save(user);
    }

    public boolean login(LoginRequest loginRequest) {
        return true;
    }
}
