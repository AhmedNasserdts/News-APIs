package com.upwork.newsports.controller;

import com.upwork.newsports.auth.JWTFilter;
import com.upwork.newsports.auth.JWTService;
import com.upwork.newsports.model.Log;
import com.upwork.newsports.model.User;
import com.upwork.newsports.service.LogService;
import com.upwork.newsports.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    JWTService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final LogService logService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userService.emailExists(request.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already exists"));
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(new BCryptPasswordEncoder(12).encode(request.getPassword()))
                .roles(Set.of("ROLE_USER"))
                .active(true)
                .build();

        User savedUser = userService.saveUser(user);

        // Log the registration
        Log log = Log.builder()
                .type("REGISTER")
                .actorId(savedUser.getId())
                .details("User registered: " + savedUser.getEmail())
                .ts(Instant.now())
                .build();
        logService.saveLog(log);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        var user = userService.getUserByEmail(request.getEmail());

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            Log log = Log.builder()
                    .type("LOGIN")
                    .actorId(user.get().getId())
                    .details("User logged in: " + user.get().getEmail())
                    .ts(Instant.now())
                    .build();
            logService.saveLog(log);

            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "token", jwtService.generateToken(),
                    "user", user.get()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Generate JWT token
        // Log the login


        // In a real application, you would generate a JWT token here
    }

    @Data
    public static class RegisterRequest {
        private String name;
        private String email;
        private String password;
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }
}