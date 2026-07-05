package com.stockbrokerage.backend.controller;

import com.stockbrokerage.backend.dto.StockRequest;
import com.stockbrokerage.backend.dto.StockResponse;
import com.stockbrokerage.backend.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StockController {

    private final StockService stockService;

    // Add Stock
    @PostMapping
    public ResponseEntity<StockResponse> addStock(
            @Valid @RequestBody StockRequest request) {

        return ResponseEntity.ok(stockService.addStock(request));
    }

    // Get All Stocks
    @GetMapping
    public ResponseEntity<List<StockResponse>> getAllStocks() {

        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @GetMapping("/page")
public ResponseEntity<Page<StockResponse>> getStocksWithPagination(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

    return ResponseEntity.ok(
            stockService.getStocksWithPagination(page, size)
    );
}

    // Get Stock By ID
    @GetMapping("/{id}")
    public ResponseEntity<StockResponse> getStockById(
            @PathVariable Long id) {

        return ResponseEntity.ok(stockService.getStockById(id));
    }

    // Delete Stock
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStock(
            @PathVariable Long id) {

        return ResponseEntity.ok(stockService.deleteStock(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<StockResponse> updateStock(
        @PathVariable Long id,
        @Valid @RequestBody StockRequest request) {

    return ResponseEntity.ok(stockService.updateStock(id, request));
    }
    @GetMapping("/search")
public ResponseEntity<List<StockResponse>> searchStocks(
        @RequestParam String keyword) {

    return ResponseEntity.ok(
            stockService.searchStocks(keyword)
    );
}
@GetMapping("/sector")
public ResponseEntity<List<StockResponse>> getStocksBySector(
        @RequestParam String sector) {

    return ResponseEntity.ok(
            stockService.getStocksBySector(sector)
    );
}
@GetMapping("/sort")
public ResponseEntity<List<StockResponse>> sortStocks(
        @RequestParam String field,
        @RequestParam(defaultValue = "asc") String direction) {

    return ResponseEntity.ok(
            stockService.sortStocks(field, direction)
    );
}
}