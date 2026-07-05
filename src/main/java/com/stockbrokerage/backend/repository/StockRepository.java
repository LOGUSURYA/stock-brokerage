package com.stockbrokerage.backend.repository;

import com.stockbrokerage.backend.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByStockSymbol(String stockSymbol);
    List<Stock> findByCompanyNameContainingIgnoreCaseOrStockSymbolContainingIgnoreCase(
        String companyName,
        String stockSymbol
  );
   List<Stock> findBySectorIgnoreCase(String sector);
}