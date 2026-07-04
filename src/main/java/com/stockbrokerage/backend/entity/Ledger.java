package com.stockbrokerage.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Client whose ledger this belongs to
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientAccount client;

    // Description of transaction
    @Column(nullable = false)
    private String description;

    // Money credited
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal credit;

    // Money debited
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal debit;

    // Balance after transaction
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    // Date and Time
    @Column(nullable = false)
    private LocalDateTime transactionDate;
}