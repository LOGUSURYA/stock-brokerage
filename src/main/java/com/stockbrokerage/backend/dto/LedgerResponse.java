package com.stockbrokerage.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerResponse {

    private Long ledgerId;

    private String description;

    private BigDecimal credit;

    private BigDecimal debit;

    private BigDecimal balance;

    private LocalDateTime transactionDate;

}