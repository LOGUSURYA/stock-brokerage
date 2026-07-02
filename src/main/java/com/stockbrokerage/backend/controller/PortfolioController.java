package com.stockbrokerage.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @GetMapping
    public String getPortfolio() {
        return "Portfolio endpoint placeholder";
    }
}
