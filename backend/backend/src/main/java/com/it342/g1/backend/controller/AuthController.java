package com.it342.g1.backend.controller;

import com.it342.g1.backend.dto.AuthResponse;
import com.it342.g1.backend.dto.LoginRequest;
import com.it342.g1.backend.dto.RegisterRequest;
import com.it342.g1.backend.dto.UserDTO;
import com.it342.g1.backend.model.User;
import com.it342.g1.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository
    ) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            if (request.getEmail() == null || request.getEmail().isBlank()
                    || request.getUsername() == null || request.getUsername().isBlank()
                    || request.getPassword() == null || request.getPassword().isBlank()) {
                return ResponseEntity.badRequest().body(new AuthResponse(null, "Email, username, and password are required", null));
            }
            
            User user = userService.registerUser(
                    request.getEmail(),
                    request.getUsername(),
                    request.getPassword(),
                    request.getFirstName(),
                    request.getLastName()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponse(null, "User registered successfully", UserDTO.fromUser(user)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new AuthResponse(null, e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) {
        try {
            if (request.getEmail() == null || request.getEmail().isBlank()
                    || request.getPassword() == null || request.getPassword().isBlank()) {
                return ResponseEntity.badRequest().body(new AuthResponse(null, "Email and password required", null));
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            securityContextRepository.saveContext(context, httpRequest, httpResponse);

            Optional<User> user = userService.getUserByEmail(request.getEmail());
            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new AuthResponse(null, "Invalid credentials", null));
            }

            return ResponseEntity.ok(new AuthResponse(null, "Login successful", UserDTO.fromUser(user.get())));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, "Invalid credentials", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(null, "Error: " + e.getMessage(), null));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(new AuthResponse(null, "Logout successful", null));
    }
}

