package com.stockbrokerage.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @GetMapping
    public String getAllStocks() {
        return "Stocks endpoint placeholder";
    }
}
