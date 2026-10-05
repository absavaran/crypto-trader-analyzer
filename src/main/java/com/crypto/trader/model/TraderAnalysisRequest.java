package com.crypto.trader.model;

public record TraderAnalysisRequest(
        String coinName,
        String marketTrend,
        String supportLevel,
        String resistanceLevel,
        Double riskReward,
        Double volumeStrength,
        String sentiment
) {}
