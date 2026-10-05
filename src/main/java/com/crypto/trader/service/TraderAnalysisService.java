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
        String strengths;
        String weaknesses;
        String recommendation;
        String riskLevel;
        double confidence;

        if (trend.contains("bull")) {
            summary = "The market structure for " + coinName + " is bullish. The price action remains constructive and supports continuation in the current trend.";
            strategy = "Favor trend continuation entries on pullbacks near support, with confirmation from volume and momentum. Use resistance breaks only when confirmed by stronger participation.";
            strengths = "Strong directional bias, better risk placement, and efficient pullback entries in a healthy trend environment.";
            weaknesses = "A late entry or weak confirmation can reduce the quality of the setup, especially if momentum fades after the initial move.";
            recommendation = "Continue with disciplined trend-following entries while avoiding aggressive chases above clearly defined resistance.";
            riskLevel = "Moderate";
            confidence = 86.0;
        } else if (trend.contains("bear")) {
            summary = "The market structure for " + coinName + " is bearish. Momentum is under pressure, and short setups are favored only when technical confirmation stands out.";
            strategy = "Prioritize breakdowns under structural support and avoid bottom fishing without a clear invalidation point.";
            strengths = "Clear downside flow, good risk discipline, and strong entry opportunities during failed breakouts.";
            weaknesses = "Countertrend rallies may reduce momentum and generate false signals if volume weakens rapidly.";
            recommendation = "Keep exposure selective and focus on strong downside confirmation rather than emotional reversals.";
            riskLevel = "Moderate";
            confidence = 82.0;
        } else {
            summary = "The market for " + coinName + " is range-bound. The asset is not demonstrating a strong directional bias, so execution should remain selective.";
            strategy = "Trade only in clearly defined support and resistance zones, with strict stop placement and reduced risk sizing.";
            strengths = "Low operational noise, better control of entry timing, and higher probability in well-defined ranges.";
            weaknesses = "Limited trend strength can lead to repeated false breakouts and missed opportunities if timing is careless.";
            recommendation = "Wait for a clean breakout or rejection at clear boundaries before committing capital.";
            riskLevel = "High";
            confidence = 71.0;
        }

        if (riskReward >= 2.5) {
            strengths += " The risk-to-reward profile is attractive and supports sustainable capital preservation.";
            confidence += 4;
        } else if (riskReward >= 2.0) {
            strengths += " Risk management remains acceptable, though optimization could improve long-term outcome.";
        } else {
            weaknesses += " The current risk-to-reward structure is weak and may compress the strategy's long-term profitability.";
            confidence -= 6;
        }

        if (volumeStrength >= 70) {
            strengths += " Volume confirms trend participation and adds weight to the decision process.";
            confidence += 3;
        } else if (volumeStrength < 35) {
            weaknesses += " Weak participation reduces conviction and increases the chance of false signals.";
            confidence -= 5;
        }

        if (sentiment != null && !sentiment.isBlank()) {
            summary += " Market sentiment is described as \"" + sentiment + "\".";
        }

        if (confidence > 100) confidence = 100;
        if (confidence < 0) confidence = 0;

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
