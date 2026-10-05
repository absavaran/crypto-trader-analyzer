package com.crypto.trader.model;

public record PortfolioPosition(
        String symbol,
        String side,
        double amount,
        double entry,
        double mark,
        double pnl,
        String risk
) {}
