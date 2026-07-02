package com.stockbrokerage.backend.service;

import com.stockbrokerage.backend.dto.AuthResponse;
import com.stockbrokerage.backend.dto.LoginRequest;
import com.stockbrokerage.backend.dto.RegisterRequest;
import com.stockbrokerage.backend.entity.User;
import com.stockbrokerage.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.stockbrokerage.backend.config.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {

        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }
    public AuthResponse login(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid Email"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid Password");
    }

  String token = jwtUtil.generateToken(user.getEmail());

    return new AuthResponse(
            token,
            user.getName(),
            user.getEmail(),
            user.getRole().name()
    );
  }
}


