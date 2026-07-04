package com.stockbrokerage.backend.repository;

import com.stockbrokerage.backend.entity.ClientAccount;
import com.stockbrokerage.backend.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LedgerRepository extends JpaRepository<Ledger, Long> {

    List<Ledger> findByClientOrderByTransactionDateDesc(
            ClientAccount client
    );

}