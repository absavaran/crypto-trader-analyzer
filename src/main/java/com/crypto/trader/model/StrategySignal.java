package com.crypto.trader.model;

public record StrategySignal(
        String coin,
        String trend,
        double confidence,
        double riskReward,
        double volumeStrength,
        String recommendation
) {}
