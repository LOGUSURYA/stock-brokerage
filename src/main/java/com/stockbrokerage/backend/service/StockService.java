package com.stockbrokerage.backend.service;

import com.stockbrokerage.backend.dto.StockRequest;
import com.stockbrokerage.backend.dto.StockResponse;
import com.stockbrokerage.backend.entity.Stock;
import com.stockbrokerage.backend.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    public Page<StockResponse> getStocksWithPagination(int page, int size) {

    Pageable pageable = PageRequest.of(page, size);

    return stockRepository.findAll(pageable)
            .map(this::mapToResponse);
}
public List<StockResponse> searchStocks(String keyword) {

    return stockRepository
            .findByCompanyNameContainingIgnoreCaseOrStockSymbolContainingIgnoreCase(
                    keyword,
                    keyword
            )
            .stream()
            .map(this::mapToResponse)
            .toList();
}
public List<StockResponse> getStocksBySector(String sector) {

    return stockRepository.findBySectorIgnoreCase(sector)
            .stream()
            .map(this::mapToResponse)
            .toList();
}
public List<StockResponse> sortStocks(String field, String direction) {

    Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(field).descending()
            : Sort.by(field).ascending();

    return stockRepository.findAll(sort)
            .stream()
            .map(this::mapToResponse)
            .toList();
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