package com.stockbrokerage.backend.service;

import com.stockbrokerage.backend.entity.RefreshToken;
import com.stockbrokerage.backend.entity.User;
import com.stockbrokerage.backend.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    // Create a new refresh token
    public RefreshToken createRefreshToken(User user) {

        // Delete old token if it exists
        refreshTokenRepository.findByUser(user)
                .ifPresent(refreshTokenRepository::delete);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    // Find refresh token
    public RefreshToken findByToken(String token) {

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid Refresh Token"));
    }

    // Check if token has expired
    public void verifyExpiration(RefreshToken refreshToken) {

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            refreshTokenRepository.delete(refreshToken);

            throw new RuntimeException("Refresh Token Expired");
        }
    }
}