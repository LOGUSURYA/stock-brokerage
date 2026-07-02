package com.stockbrokerage.backend.repository;

import com.stockbrokerage.backend.entity.ClientAccount;
import com.stockbrokerage.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientAccount, Long> {

    Optional<ClientAccount> findByUser(User user);

    Optional<ClientAccount> findByPanNumber(String panNumber);

    Optional<ClientAccount> findByAadhaarNumber(String aadhaarNumber);

}