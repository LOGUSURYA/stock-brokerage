package com.stockbrokerage.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import com.stockbrokerage.backend.enums.OrderStatus;
import com.stockbrokerage.backend.enums.OrderType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Client placing the order
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private ClientAccount client;

    // Stock being traded
    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    // BUY or SELL
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType orderType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
}