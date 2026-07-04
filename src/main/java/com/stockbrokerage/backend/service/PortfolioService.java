package com.stockbrokerage.backend.service;

import com.stockbrokerage.backend.dto.HoldingResponse;
import com.stockbrokerage.backend.entity.ClientAccount;
import com.stockbrokerage.backend.entity.Holding;
import com.stockbrokerage.backend.entity.Stock;
import com.stockbrokerage.backend.repository.HoldingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final HoldingRepository holdingRepository;

    // Update holdings after BUY
    public void addHolding(
            ClientAccount client,
            Stock stock,
            Integer quantity,
            BigDecimal price) {

        Holding holding = holdingRepository
                .findByClientAndStock(client, stock)
                .orElse(null);

        if (holding == null) {

            holding = Holding.builder()
                    .client(client)
                    .stock(stock)
                    .quantityOwned(quantity)
                    .averagePrice(price)
                    .build();

        } else {

            holding.setQuantityOwned(
                    holding.getQuantityOwned() + quantity
            );

        }

        holdingRepository.save(holding);

    }

    // View portfolio
    public List<HoldingResponse> getPortfolio(ClientAccount client) {

        return holdingRepository.findByClient(client)
                .stream()
                .map(h -> HoldingResponse.builder()
                        .holdingId(h.getId())
                        .stockSymbol(h.getStock().getStockSymbol())
                        .companyName(h.getStock().getCompanyName())
                        .quantityOwned(h.getQuantityOwned())
                        .averagePrice(h.getAveragePrice())
                        .build())
                .toList();

    }

}