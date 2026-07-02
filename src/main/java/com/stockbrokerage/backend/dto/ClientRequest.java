package com.stockbrokerage.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequest {

    @NotNull(message = "User Id is required")
    private Long userId;

    @NotBlank(message = "PAN Number is required")
    private String panNumber;

    @NotBlank(message = "Aadhaar Number is required")
    private String aadhaarNumber;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Balance is required")
    private BigDecimal balance;

}