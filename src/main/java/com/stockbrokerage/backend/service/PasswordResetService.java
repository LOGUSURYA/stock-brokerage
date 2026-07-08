package com.stockbrokerage.backend.service;

import com.stockbrokerage.backend.entity.PasswordResetToken;
import com.stockbrokerage.backend.entity.User;
import com.stockbrokerage.backend.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordResetService {

    private final PasswordResetTokenRepository repository;

    public PasswordResetToken createToken(User user) {

        repository.findByUser(user)
                .ifPresent(repository::delete);

        PasswordResetToken token = PasswordResetToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();

        return repository.save(token);
    }

    public PasswordResetToken verifyToken(String token) {

        PasswordResetToken resetToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid Reset Token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            repository.delete(resetToken);

            throw new RuntimeException("Reset Token Expired");
        }

        return resetToken;
    }

    public void deleteToken(User user) {
        repository.deleteByUser(user);
    }
}