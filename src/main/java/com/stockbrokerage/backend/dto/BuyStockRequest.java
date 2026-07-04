package com.stockbrokerage.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyStockRequest {

    @NotNull(message = "Client Id is required")
    private Long clientId;

    @NotNull(message = "Stock Id is required")
    private Long stockId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than zero")
    private Integer quantity;

}