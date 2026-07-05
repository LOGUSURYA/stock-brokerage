package com.stockbrokerage.backend.repository;

import com.stockbrokerage.backend.entity.ClientAccount;
import com.stockbrokerage.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.math.BigDecimal;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientAccount, Long> {

    Optional<ClientAccount> findByUser(User user);

    Optional<ClientAccount> findByPanNumber(String panNumber);

    Optional<ClientAccount> findByAadhaarNumber(String aadhaarNumber);

    @Query("SELECT COALESCE(SUM(c.balance), 0) FROM ClientAccount c")
   BigDecimal getTotalClientBalance();
   List<ClientAccount> findByUser_NameContainingIgnoreCaseOrUser_EmailContainingIgnoreCase(
        String name,
        String email
);

}