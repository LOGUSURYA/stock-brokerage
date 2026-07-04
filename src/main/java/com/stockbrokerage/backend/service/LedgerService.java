package com.stockbrokerage.backend.service;

import com.stockbrokerage.backend.dto.LedgerResponse;
import com.stockbrokerage.backend.entity.ClientAccount;
import com.stockbrokerage.backend.entity.Ledger;
import com.stockbrokerage.backend.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {

    private final LedgerRepository ledgerRepository;

    // Debit Entry (Buy Stock)
    public void addDebitEntry(
            ClientAccount client,
            String description,
            BigDecimal amount,
            BigDecimal balance) {

        Ledger ledger = Ledger.builder()
                .client(client)
                .description(description)
                .credit(BigDecimal.ZERO)
                .debit(amount)
                .balance(balance)
                .transactionDate(LocalDateTime.now())
                .build();

        ledgerRepository.save(ledger);
    }

    // Credit Entry (Sell Stock)
    public void addCreditEntry(
            ClientAccount client,
            String description,
            BigDecimal amount,
            BigDecimal balance) {

        Ledger ledger = Ledger.builder()
                .client(client)
                .description(description)
                .credit(amount)
                .debit(BigDecimal.ZERO)
                .balance(balance)
                .transactionDate(LocalDateTime.now())
                .build();

        ledgerRepository.save(ledger);
    }

    // Get Client Ledger
    public List<LedgerResponse> getLedger(ClientAccount client) {

        return ledgerRepository
                .findByClientOrderByTransactionDateDesc(client)
                .stream()
                .map(ledger -> LedgerResponse.builder()
                        .ledgerId(ledger.getId())
                        .description(ledger.getDescription())
                        .credit(ledger.getCredit())
                        .debit(ledger.getDebit())
                        .balance(ledger.getBalance())
                        .transactionDate(ledger.getTransactionDate())
                        .build())
                .toList();
    }

}