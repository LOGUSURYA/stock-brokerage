package com.stockbrokerage.backend.service;

import com.stockbrokerage.backend.dto.BuyStockRequest;
import com.stockbrokerage.backend.dto.OrderResponse;
import com.stockbrokerage.backend.entity.ClientAccount;
import com.stockbrokerage.backend.entity.Order;
import com.stockbrokerage.backend.entity.Stock;
import com.stockbrokerage.backend.enums.OrderStatus;
import com.stockbrokerage.backend.enums.OrderType;
import com.stockbrokerage.backend.repository.ClientRepository;
import com.stockbrokerage.backend.repository.OrderRepository;
import com.stockbrokerage.backend.repository.StockRepository;

import com.stockbrokerage.backend.service.PortfolioService;
import com.stockbrokerage.backend.service.LedgerService;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    private final StockRepository stockRepository;

    private final PortfolioService portfolioService;

    private final LedgerService ledgerService;
   
    @Transactional
    public OrderResponse createBuyOrder(BuyStockRequest request) {

        ClientAccount client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client Not Found"));

        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new RuntimeException("Stock Not Found"));

        BigDecimal totalAmount =
                stock.getCurrentPrice()
                        .multiply(BigDecimal.valueOf(request.getQuantity()));

            if (client.getBalance().compareTo(totalAmount) < 0) {
                  throw new RuntimeException("Insufficient Balance");
             }

             if (stock.getAvailableQuantity() < request.getQuantity()) {
                   throw new RuntimeException("Insufficient Stock Quantity");
               }
         
               client.setBalance(
                 client.getBalance().subtract(totalAmount)
               );

               stock.setAvailableQuantity(
                     stock.getAvailableQuantity() - request.getQuantity()
               );
            
            clientRepository.save(client);
            stockRepository.save(stock);


        Order order = Order.builder()
                .client(client)
                .stock(stock)
                .orderType(OrderType.BUY)
                .quantity(request.getQuantity())
                .price(stock.getCurrentPrice())
                .totalAmount(totalAmount)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.COMPLETED)
                .build();

        orderRepository.save(order);

        portfolioService.addHolding(
        client,
        stock,
        request.getQuantity(),
        stock.getCurrentPrice()
       );
       ledgerService.addDebitEntry(
        client,
        "Bought " + request.getQuantity() + " shares of " + stock.getStockSymbol(),
        totalAmount,
        client.getBalance()
       );
        return OrderResponse.builder()
                .orderId(order.getId())
                .clientName(client.getUser().getName())
                .stockSymbol(stock.getStockSymbol())
                .orderType(order.getOrderType())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .build();
    }
    public List<OrderResponse> getAllOrders() {

    return orderRepository.findAll()
            .stream()
            .map(order -> OrderResponse.builder()
                    .orderId(order.getId())
                    .clientName(order.getClient().getUser().getName())
                    .stockSymbol(order.getStock().getStockSymbol())
                    .orderType(order.getOrderType())
                    .quantity(order.getQuantity())
                    .price(order.getPrice())
                    .totalAmount(order.getTotalAmount())
                    .status(order.getStatus())
                    .orderDate(order.getOrderDate())
                    .build())
            .toList();
     }

}