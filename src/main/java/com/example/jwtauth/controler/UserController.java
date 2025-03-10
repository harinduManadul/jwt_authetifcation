package com.example.jwtauth.controler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtauth.jwtAutentication.JwtUtil;
import com.example.jwtauth.model.User;
import com.example.jwtauth.repository.UserRepository;
import com.example.jwtauth.requestModel.LoginRequest;
import com.example.jwtauth.service.CustomUserDetailsService;
import com.example.jwtauth.service.UserSevice;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserSevice uService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public UserController(UserSevice uService,
        AuthenticationManager authenticationManager, 
        UserDetailsService userDetailsService,
        UserRepository userRepository,
        JwtUtil jwtUtil,
        PasswordEncoder passwordEncoder,
        CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.uService = uService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public User saveUser(@RequestBody User user){
        uService.save(user);
        return user;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest){
        try {
            Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
            if (user.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Authentication failed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Authentication failed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }
            String token = jwtUtil.generateToken(user.get().getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Authentication failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

    }
}
