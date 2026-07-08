package com.stockbrokerage.backend.repository;

import com.stockbrokerage.backend.entity.PasswordResetToken;
import com.stockbrokerage.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUser(User user);
    
    @Modifying
    @Transactional
    void deleteByUser(User user);
}
