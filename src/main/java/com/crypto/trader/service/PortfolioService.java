package com.crypto.trader.service;

import com.crypto.trader.model.PortfolioPosition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {

    public List<PortfolioPosition> getPortfolio() {
        return List.of(
                new PortfolioPosition("BTC", "Long", 0.42, 64200.00, 67342.18, 1312.52, "Low"),
                new PortfolioPosition("ETH", "Long", 4.60, 3380.00, 3516.45, 627.27, "Medium"),
                new PortfolioPosition("SOL", "Long", 28.00, 146.00, 158.42, 349.76, "Medium"),
                new PortfolioPosition("XRP", "Short", 980.00, 0.68, 0.61, 68.64, "High"),
                new PortfolioPosition("ADA", "Long", 420.00, 0.71, 0.74, 12.60, "Low")
        );
    }
}
