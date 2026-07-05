package com.stockbrokerage.backend.service;

import com.stockbrokerage.backend.dto.DashboardResponse;
import com.stockbrokerage.backend.enums.OrderType;
import com.stockbrokerage.backend.repository.ClientRepository;
import com.stockbrokerage.backend.repository.HoldingRepository;
import com.stockbrokerage.backend.repository.OrderRepository;
import com.stockbrokerage.backend.repository.StockRepository;
import com.stockbrokerage.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final HoldingRepository holdingRepository;

    public DashboardResponse getDashboardSummary() {

        return DashboardResponse.builder()
                .totalUsers(userRepository.count())
                .totalClients(clientRepository.count())
                .totalStocks(stockRepository.count())
                .totalOrders(orderRepository.count())
                .buyOrders(orderRepository.countByOrderType(OrderType.BUY))
                .sellOrders(orderRepository.countByOrderType(OrderType.SELL))
                .totalClientBalance(clientRepository.getTotalClientBalance())
                .totalHoldings(holdingRepository.count())
                .build();
    }
}