package com.stockbrokerage.backend.dto;

import com.stockbrokerage.backend.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String phone;
    private Role role;
}