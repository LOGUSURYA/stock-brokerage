package com.stockbrokerage.backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private Long totalUsers;

    private Long totalClients;

    private Long totalStocks;

    private Long totalOrders;

    private Long buyOrders;

    private Long sellOrders;

    private BigDecimal totalClientBalance;

    private Long totalHoldings;
}