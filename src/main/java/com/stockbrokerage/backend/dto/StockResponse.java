package com.stockbrokerage.backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockResponse {

    private Long id;

    private String companyName;

    private String stockSymbol;

    private BigDecimal currentPrice;

    private Integer availableQuantity;

    private String sector;

    private LocalDate createdDate;
}