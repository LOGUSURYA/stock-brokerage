package com.stockbrokerage.backend.service;

import com.stockbrokerage.backend.dto.AuthResponse;
import com.stockbrokerage.backend.dto.ForgotPasswordRequest;
import com.stockbrokerage.backend.dto.LoginRequest;
import com.stockbrokerage.backend.dto.RefreshTokenRequest;
import com.stockbrokerage.backend.dto.RegisterRequest;
import com.stockbrokerage.backend.dto.ResetPasswordRequest;
import com.stockbrokerage.backend.entity.User;
import com.stockbrokerage.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.stockbrokerage.backend.config.JwtUtil;
import com.stockbrokerage.backend.entity.PasswordResetToken;
import com.stockbrokerage.backend.entity.RefreshToken;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordResetService passwordResetService;

    private final EmailService emailService;
     private static final Logger logger =
            LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

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
        logger.info("New user registered: {}", user.getEmail());

        return "User Registered Successfully";
    }
    public AuthResponse login(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid Email"));

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new RuntimeException("Invalid Password");
    }

  String accessToken = jwtUtil.generateToken(user.getEmail()); 
  RefreshToken refreshToken =
        refreshTokenService.createRefreshToken(user);
  logger.info("User logged in: {}", user.getEmail());

      return AuthResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken.getToken())
        .name(user.getName())
        .email(user.getEmail())
        .role(user.getRole().name())
        .build();
  }
  public AuthResponse refreshToken(RefreshTokenRequest request) {

    RefreshToken refreshToken =
            refreshTokenService.findByToken(request.getRefreshToken());

    refreshTokenService.verifyExpiration(refreshToken);

    User user = refreshToken.getUser();

    String newAccessToken = jwtUtil.generateToken(user.getEmail());

    return AuthResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(refreshToken.getToken())
            .name(user.getName())
            .email(user.getEmail())
            .role(user.getRole().name())
            .build();
}
public String forgotPassword(ForgotPasswordRequest request) {

    System.out.println("Received Email = [" + request.getEmail() + "]");

    System.out.println("Users in DB:");
    userRepository.findAll().forEach(u ->
            System.out.println(u.getEmail()));

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Email not found"));

    PasswordResetToken resetToken =
            passwordResetService.createToken(user);

    String resetLink =
            "http://localhost:3000/reset-password?token="
                    + resetToken.getToken();

    emailService.sendPasswordResetEmail(
            user.getEmail(),
            resetLink
    );

    return "Password reset link sent successfully.";
}
public String resetPassword(ResetPasswordRequest request) {

    PasswordResetToken resetToken =
            passwordResetService.verifyToken(request.getToken());

    User user = resetToken.getUser();

    user.setPassword(
            passwordEncoder.encode(request.getNewPassword())
    );

    userRepository.save(user);

    passwordResetService.deleteToken(user);

    return "Password updated successfully.";
}
}


