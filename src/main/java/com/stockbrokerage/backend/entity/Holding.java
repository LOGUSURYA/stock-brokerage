package com.stockbrokerage.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "holdings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Client who owns the stock
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientAccount client;

    // Stock owned
    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    // Number of shares owned
    @Column(nullable = false)
    private Integer quantityOwned;

    // Average buying price
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal averagePrice;
}