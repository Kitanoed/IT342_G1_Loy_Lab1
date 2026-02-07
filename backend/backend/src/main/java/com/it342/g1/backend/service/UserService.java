package com.it342.g1.backend.service;

import com.it342.g1.backend.model.User;
import com.it342.g1.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Register a new user
     */
    public User registerUser(String email, String username, String password, String firstName, String lastName) {
        // Check if email or username already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already taken");
        }
        
        // Create new user with encrypted password
        User user = new User(email, username, passwordEncoder.encode(password), firstName, lastName);
        return userRepository.save(user);
    }
    
    /**
     * Find user by ID
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Find user by email
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
