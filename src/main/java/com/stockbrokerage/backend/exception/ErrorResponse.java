package com.stockbrokerage.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private boolean success;

    private String message;

    private LocalDateTime timestamp;

}