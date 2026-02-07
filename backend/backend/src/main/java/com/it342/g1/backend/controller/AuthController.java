package com.it342.g1.backend.controller;

import com.it342.g1.backend.dto.AuthResponse;
import com.it342.g1.backend.dto.LoginRequest;
import com.it342.g1.backend.dto.RegisterRequest;
import com.it342.g1.backend.dto.UserDTO;
import com.it342.g1.backend.model.User;
import com.it342.g1.backend.security.JwtUtil;
import com.it342.g1.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            if (request.getEmail() == null || request.getEmail().isEmpty()) {
                return ResponseEntity.badRequest().body(new AuthResponse(null, "Email is required", null));
            }
            
            User user = userService.registerUser(
                    request.getEmail(),
                    request.getUsername(),
                    request.getPassword(),
                    request.getFirstName(),
                    request.getLastName()
            );
            
            String token = jwtUtil.generateToken(user.getId(), user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponse(token, "User registered successfully", UserDTO.fromUser(user)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponse(null, e.getMessage(), null));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            if (request.getEmail() == null || request.getPassword() == null) {
                return ResponseEntity.badRequest().body(new AuthResponse(null, "Email and password required", null));
            }
            
            Optional<User> user = userService.authenticateUser(request.getEmail(), request.getPassword());
            
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthResponse(null, "Invalid credentials", null));
            }
            
            User u = user.get();
            String token = jwtUtil.generateToken(u.getId(), u.getEmail());
            return ResponseEntity.ok(new AuthResponse(token, "Login successful", UserDTO.fromUser(u)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(null, "Error: " + e.getMessage(), null));
        }
    }
}

