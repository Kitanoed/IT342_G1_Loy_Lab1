package com.it342.g1.backend.controller;

import com.it342.g1.backend.dto.UserDTO;
import com.it342.g1.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null
                    || !authentication.isAuthenticated()
                    || authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
            }

            String email = authentication.getName();
            return userService.getUserByEmail(email)
                    .<ResponseEntity<?>>map(user -> ResponseEntity.ok(UserDTO.fromUser(user)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
