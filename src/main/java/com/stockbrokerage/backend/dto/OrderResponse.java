package com.stockbrokerage.backend.dto;

import com.stockbrokerage.backend.enums.OrderStatus;
import com.stockbrokerage.backend.enums.OrderType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long orderId;

    private String clientName;

    private String stockSymbol;

    private OrderType orderType;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private LocalDateTime orderDate;

}