package com.crypto.trader.model;

public record MarketSnapshot(
        String symbol,
        String name,
        double price,
        double change24h,
        double volume24h,
        double marketCap,
        String trend
) {}
