package com.example.jwtauth.controler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtauth.model.User;
import com.example.jwtauth.requestModel.LoginRequest;
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

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest){
        return uService.login(loginRequest);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/request01")
    public ResponseEntity<Map<String, String>> request01(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Request 01");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/request02")
    public ResponseEntity<Map<String, String>> request02(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Request 02");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/request03")
    public ResponseEntity<Map<String, String>> request03(){
        Map<String, String> response = new HashMap<>();
        response.put("message", "Request 03");
        return ResponseEntity.ok(response);
    }
}
