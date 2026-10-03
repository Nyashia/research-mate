package com.nyashia.resarch_mate.service;

import com.nyashia.resarch_mate.dto.AuthResponse;
import com.nyashia.resarch_mate.dto.LoginRequest;
import com.nyashia.resarch_mate.dto.RegisterRequest;
import com.nyashia.resarch_mate.model.User;
import com.nyashia.resarch_mate.repository.UserRepository;
import com.nyashia.resarch_mate.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();

        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setCreatedAt(Instant.now());

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());

        long expiresAt =
                System.currentTimeMillis() + jwtService.getExpirationMs();

        return new AuthResponse(
                token,
                user.getEmail(),
                expiresAt
        );
    }

    public AuthResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.email(),
                                request.password()
                        )
                );

        String email = authentication.getName();

        String token = jwtService.generateToken(email);

        long expiresAt =
                System.currentTimeMillis() + jwtService.getExpirationMs();

        return new AuthResponse(
                token,
                email,
                expiresAt
        );
    }
}