package com.example.jwtauth.controler;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtauth.model.User;
import com.example.jwtauth.service.UserSevice;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserSevice uService;

    public UserController(UserSevice uService) {
        this.uService = uService;
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody User user){
        uService.save(user);
        return user;
    }
}
