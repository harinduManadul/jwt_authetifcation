package com.example.jwtauth.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwtauth.model.User;
import com.example.jwtauth.repository.UserRepository;
import com.example.jwtauth.requestModel.LoginRequest;
import com.example.jwtauth.jwtAutentication.JwtUtil;

@Service
public class UserSevice {

    private final UserRepository uRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public UserSevice(UserRepository uRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.uRepository = uRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return uRepository.save(user);
    }

    public ResponseEntity<Map<String, String>> login(LoginRequest loginRequest) {
        try {
            UserDetails user = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
            System.out.println("User: " + user);
            if (user == null) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Authentication failed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Authentication failed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
            String token = jwtUtil.generateToken(user);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", user.getUsername());
            response.put("roles", user.getAuthorities().toString());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Authentication failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}
