package com.stockbrokerage.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeResponse {

    private String message;

    private OrderResponse order;

}