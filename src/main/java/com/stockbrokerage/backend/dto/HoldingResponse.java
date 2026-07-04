package com.stockbrokerage.backend.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoldingResponse {

    private Long holdingId;

    private String stockSymbol;

    private String companyName;

    private Integer quantityOwned;

    private BigDecimal averagePrice;

}