package com.crypto.trader.service;

import com.crypto.trader.model.TraderAnalysisRequest;
import com.crypto.trader.model.TraderAnalysisResponse;
import org.springframework.stereotype.Service;

@Service
public class TraderAnalysisService {

    public TraderAnalysisResponse analyze(TraderAnalysisRequest request) {
        String coinName = request.coinName() == null || request.coinName().isBlank() ? "Market" : request.coinName();
        String trend = normalizeTrend(request.marketTrend());
        double riskReward = request.riskReward() != null ? request.riskReward() : 2.0;
        double volumeStrength = request.volumeStrength() != null ? request.volumeStrength() : 50.0;
        String sentiment = request.sentiment() == null || request.sentiment().isBlank() ? "Neutral" : request.sentiment();

        String summary;
        String strategy;
        String strengths = "";
        String weaknesses = "";
        String recommendation;
        String riskLevel;
        double confidence = 75.0;

        if (trend.contains("bull")) {
            summary = "The market structure for " + coinName + " is bullish. Price action remains constructive and the broader trend supports continuation.";
            strategy = "Maintain a trend-following bias by entering on valid pullbacks near support, only after confirming momentum and volume strength.";
            strengths = "Strong directional flow, cleaner support levels, and better placement of entries during healthy trend pullbacks.";
            weaknesses = "Late entries and weak confirmation may reduce expected return, especially if range expansion occurs near resistance.";
            recommendation = "Use disciplined continuation entries while avoiding aggressive chase trades above major resistance zones.";
            riskLevel = "Moderate";
            confidence += 10;
        } else if (trend.contains("bear")) {
            summary = "The market structure for " + coinName + " is bearish. Momentum remains weak and the downside bias is technically dominant.";
            strategy = "Prioritize short setups on breakdowns under support and increase selectivity around failed bullish rebounds.";
            strengths = "Clear downside flow, strong rejection behavior, and more reliable risk placement when structure remains intact.";
            weaknesses = "Countertrend rallies may create false signals if the market absorbs selling pressure too quickly.";
            recommendation = "Only engage when downside confirmation is clean and the stop is placed beyond the most recent structural pivot.";
            riskLevel = "Moderate";
            confidence += 7;
        } else {
            summary = "The market for " + coinName + " is range-bound. Price action lacks a strong directional bias and requires more selective execution.";
            strategy = "Trade only inside well-defined support and resistance channels with tight stop management and controlled position sizing.";
            strengths = "Higher control over entry timing, clearer invalidation points, and better discipline in sideways structure.";
            weaknesses = "Limited trend definition creates more false breakouts and can delay the best trade opportunities.";
            recommendation = "Wait for a decisive breakout or rejection at a major boundary before committing new risk.";
            riskLevel = "High";
            confidence += 1;
        }

        if (riskReward >= 2.8) {
            strengths += " The risk-to-reward profile is excellent and supports repeatable long-term execution.";
            confidence += 8;
        } else if (riskReward >= 2.0) {
            strengths += " Risk management remains acceptable, though optimization around target placement could further improve outcomes.";
            confidence += 4;
        } else {
            weaknesses += " The current risk-reward structure is weak and may reduce the strategy's effectiveness over a full trading cycle.";
            confidence -= 8;
        }

        if (volumeStrength >= 70) {
            strengths += " Current volume supports the trend and strengthens conviction in the active setup.";
            confidence += 5;
        } else if (volumeStrength < 35) {
            weaknesses += " Weak participation reduces conviction and increases the chance of noise-driven false entries.";
            confidence -= 6;
        }

        if (sentiment != null && !sentiment.isBlank()) {
            summary += " Market sentiment is described as \"" + sentiment + "\".";
        }

        confidence = Math.max(0, Math.min(100, confidence));

        return new TraderAnalysisResponse(
                "Trader Review: " + coinName,
                summary,
                strategy,
                strengths,
                weaknesses,
                recommendation,
                Math.round(confidence * 10.0) / 10.0,
                riskLevel
        );
    }

    private String normalizeTrend(String rawTrend) {
        if (rawTrend == null) return "neutral";
        String value = rawTrend.trim().toLowerCase();
        if (value.contains("bull")) return "bullish";
        if (value.contains("bear")) return "bearish";
        return "neutral";
    }
}
