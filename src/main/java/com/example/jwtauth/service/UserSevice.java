package com.example.jwtauth.service;

import org.springframework.stereotype.Service;

import com.example.jwtauth.model.User;
import com.example.jwtauth.repository.UserRepository;

@Service
public class UserSevice {

    private final UserRepository uRepository;

    public UserSevice(UserRepository uRepository){
        this.uRepository = uRepository;
    }

    public User save(User user){
        return uRepository.save(user);
    }
}
