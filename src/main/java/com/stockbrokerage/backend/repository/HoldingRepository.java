package com.stockbrokerage.backend.repository;

import com.stockbrokerage.backend.entity.ClientAccount;
import com.stockbrokerage.backend.entity.Holding;
import com.stockbrokerage.backend.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HoldingRepository extends JpaRepository<Holding, Long> {

    List<Holding> findByClient(ClientAccount client);

    Optional<Holding> findByClientAndStock(
            ClientAccount client,
            Stock stock
    );

}