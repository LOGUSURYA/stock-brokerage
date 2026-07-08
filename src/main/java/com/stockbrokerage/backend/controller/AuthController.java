package com.stockbrokerage.backend.controller;

import com.stockbrokerage.backend.dto.AuthResponse;
import com.stockbrokerage.backend.dto.LoginRequest;
import com.stockbrokerage.backend.dto.RefreshTokenRequest;
import com.stockbrokerage.backend.dto.RegisterRequest;
import com.stockbrokerage.backend.service.AuthService;
import com.stockbrokerage.backend.service.EmailService;
import com.stockbrokerage.backend.dto.ForgotPasswordRequest;
import com.stockbrokerage.backend.dto.ResetPasswordRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {
     
    @Autowired
    private EmailService emailService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
           @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request)
        );
    }
     @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refreshToken(
        @RequestBody RefreshTokenRequest request) {

    return ResponseEntity.ok(
            authService.refreshToken(request)
    );
}

@GetMapping("/test-email")
public String testEmail() {

    emailService.sendPasswordResetEmail(
            "logusurya8526@gmail.com",
            "http://localhost:3000/reset-password?token=test123"
    );

    return "Email Sent Successfully";
}
@PostMapping("/forgot-password")
public ResponseEntity<String> forgotPassword(
        @RequestBody ForgotPasswordRequest request) {

    return ResponseEntity.ok(
            authService.forgotPassword(request)
    );
}
@PostMapping("/reset-password")
public ResponseEntity<String> resetPassword(
        @RequestBody ResetPasswordRequest request) {

    return ResponseEntity.ok(
            authService.resetPassword(request)
    );
}
}