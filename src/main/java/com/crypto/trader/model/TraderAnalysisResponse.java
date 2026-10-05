package com.crypto.trader.model;

public record TraderAnalysisResponse(
        String analysisTitle,
        String summary,
        String strategy,
        String strengths,
        String weaknesses,
        String recommendation,
        double confidence,
        String riskLevel
) {}
