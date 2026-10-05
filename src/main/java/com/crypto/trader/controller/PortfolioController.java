package com.crypto.trader.controller;

import com.crypto.trader.model.PortfolioPosition;
import com.crypto.trader.service.PortfolioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/portfolio")
    public List<PortfolioPosition> getPortfolio() {
        return portfolioService.getPortfolio();
    }
}
