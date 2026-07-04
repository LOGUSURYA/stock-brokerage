package com.stockbrokerage.backend.service;

import com.stockbrokerage.backend.dto.StockRequest;
import com.stockbrokerage.backend.dto.StockResponse;
import com.stockbrokerage.backend.entity.Stock;
import com.stockbrokerage.backend.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    // Add Stock
    public StockResponse addStock(StockRequest request) {

        if (stockRepository.findByStockSymbol(request.getStockSymbol()).isPresent()) {
            throw new RuntimeException("Stock Symbol already exists");
        }

        Stock stock = Stock.builder()
                .companyName(request.getCompanyName())
                .stockSymbol(request.getStockSymbol())
                .currentPrice(request.getCurrentPrice())
                .availableQuantity(request.getAvailableQuantity())
                .sector(request.getSector())
                .createdDate(LocalDate.now())
                .build();

        stockRepository.save(stock);

        return mapToResponse(stock);
    }

    // Get All Stocks
    public List<StockResponse> getAllStocks() {
        return stockRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get Stock By ID
    public StockResponse getStockById(Long id) {

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock Not Found"));

        return mapToResponse(stock);
    }

    // Delete Stock
    public String deleteStock(Long id) {

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock Not Found"));

        stockRepository.delete(stock);

        return "Stock Deleted Successfully";
    }

    private StockResponse mapToResponse(Stock stock) {

        return StockResponse.builder()
                .id(stock.getId())
                .companyName(stock.getCompanyName())
                .stockSymbol(stock.getStockSymbol())
                .currentPrice(stock.getCurrentPrice())
                .availableQuantity(stock.getAvailableQuantity())
                .sector(stock.getSector())
                .createdDate(stock.getCreatedDate())
                .build();
    }
    public StockResponse updateStock(Long id, StockRequest request) {

    Stock stock = stockRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Stock Not Found"));

    stock.setCompanyName(request.getCompanyName());
    stock.setStockSymbol(request.getStockSymbol());
    stock.setCurrentPrice(request.getCurrentPrice());
    stock.setAvailableQuantity(request.getAvailableQuantity());
    stock.setSector(request.getSector());

    stockRepository.save(stock);

    return mapToResponse(stock);
}
}