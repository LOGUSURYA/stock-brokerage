package com.stockbrokerage.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    @GetMapping
    public String getLedger() {
        return "Ledger endpoint placeholder";
    }
}
