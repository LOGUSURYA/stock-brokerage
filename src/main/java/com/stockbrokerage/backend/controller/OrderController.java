package com.stockbrokerage.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @PostMapping
    public String placeOrder() {
        return "Order endpoint placeholder";
    }
}
