package com.stockbrokerage.backend.controller;

import com.stockbrokerage.backend.dto.BuyStockRequest;
import com.stockbrokerage.backend.dto.OrderResponse;
import com.stockbrokerage.backend.dto.HoldingResponse;
import com.stockbrokerage.backend.dto.LedgerResponse;
import com.stockbrokerage.backend.entity.ClientAccount;
import com.stockbrokerage.backend.enums.OrderStatus;
import com.stockbrokerage.backend.enums.OrderType;
import com.stockbrokerage.backend.repository.ClientRepository;
import com.stockbrokerage.backend.service.LedgerService;
import com.stockbrokerage.backend.service.OrderService;
import com.stockbrokerage.backend.service.PortfolioService;
import com.stockbrokerage.backend.dto.SellStockRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TradeController {

    private final OrderService orderService;
    private final PortfolioService portfolioService;
    private final LedgerService ledgerService;
    private final ClientRepository clientRepository;

    // BUY STOCK
    @PostMapping("/buy")
    public ResponseEntity<OrderResponse> buyStock(
            @Valid @RequestBody BuyStockRequest request) {

        return ResponseEntity.ok(
                orderService.createBuyOrder(request)
        );
    }
       @PostMapping("/sell")
      public ResponseEntity<OrderResponse> sellStock(
        @Valid @RequestBody SellStockRequest request) {

         return ResponseEntity.ok(
            orderService.createSellOrder(request)
        );
       }
    // GET ALL ORDERS
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }
    @GetMapping("/orders/search/type")
public ResponseEntity<List<OrderResponse>> searchOrdersByType(
        @RequestParam OrderType orderType) {

    return ResponseEntity.ok(
            orderService.searchOrdersByType(orderType)
    );
}
    // GET PORTFOLIO
    @GetMapping("/portfolio/{clientId}")
    public ResponseEntity<List<HoldingResponse>> getPortfolio(
            @PathVariable Long clientId) {

        ClientAccount client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client Not Found"));

        return ResponseEntity.ok(
                portfolioService.getPortfolio(client)
        );
    }

    // GET LEDGER
    @GetMapping("/ledger/{clientId}")
    public ResponseEntity<List<LedgerResponse>> getLedger(
            @PathVariable Long clientId) {

        ClientAccount client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client Not Found"));

        return ResponseEntity.ok(
                ledgerService.getLedger(client)
        );
    }
    @GetMapping("/orders/filter")
public ResponseEntity<List<OrderResponse>> filterOrders(
        @RequestParam(required = false) OrderType orderType,
        @RequestParam(required = false) OrderStatus status) {

    return ResponseEntity.ok(
            orderService.filterOrders(orderType, status)
    );
}

}