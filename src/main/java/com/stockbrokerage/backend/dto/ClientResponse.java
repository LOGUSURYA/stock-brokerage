package com.stockbrokerage.backend.dto;

import com.stockbrokerage.backend.enums.AccountStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {

    private Long id;

    private String userName;

    private String email;

    private String panNumber;

    private String aadhaarNumber;

    private String address;

    private BigDecimal balance;

    private AccountStatus status;

    private LocalDate createdDate;

}