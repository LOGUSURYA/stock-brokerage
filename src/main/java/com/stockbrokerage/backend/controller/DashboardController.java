package com.stockbrokerage.backend.controller;

import com.stockbrokerage.backend.dto.DashboardResponse;
import com.stockbrokerage.backend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.stockbrokerage.backend.dto.OrderResponse;
import com.stockbrokerage.backend.service.OrderService;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final OrderService orderService;

    @GetMapping("/dashboard")
    public DashboardResponse getDashboardSummary() {
        return dashboardService.getDashboardSummary();
    }
    @GetMapping("/recent-orders")
   public List<OrderResponse> getRecentOrders() {
    return orderService.getRecentOrders();
    }
}