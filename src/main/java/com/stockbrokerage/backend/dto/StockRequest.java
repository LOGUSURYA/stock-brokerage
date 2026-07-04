package com.stockbrokerage.backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockRequest {

    @NotBlank(message = "Company Name is required")
    private String companyName;

    @NotBlank(message = "Stock Symbol is required")
    private String stockSymbol;

    @NotNull(message = "Current Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal currentPrice;

    @NotNull(message = "Available Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer availableQuantity;

    @NotBlank(message = "Sector is required")
    private String sector;
}