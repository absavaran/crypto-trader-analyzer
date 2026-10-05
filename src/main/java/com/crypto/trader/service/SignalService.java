package com.crypto.trader.service;

import com.crypto.trader.model.StrategySignal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SignalService {

    public List<StrategySignal> getSignals() {
        List<StrategySignal> signals = new ArrayList<>();
        signals.add(new StrategySignal("BTC", "Bullish", 87.0, 2.8, 78.0, "Trend continuation near support"));
        signals.add(new StrategySignal("ETH", "Bullish", 83.0, 2.5, 74.0, "Breakout confirmation preferred"));
        signals.add(new StrategySignal("SOL", "Bullish", 80.0, 2.6, 82.0, "Momentum-led long setup"));
        signals.add(new StrategySignal("XRP", "Neutral", 68.0, 1.9, 46.0, "Await clearer directional break"));
        signals.add(new StrategySignal("ADA", "Bullish", 72.0, 2.2, 63.0, "Range breakout watch"));
        return signals;
    }
}
