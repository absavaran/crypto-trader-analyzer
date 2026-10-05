package com.crypto.trader.service;

import com.crypto.trader.model.MarketSnapshot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketSnapshotService {

    public List<MarketSnapshot> getSnapshots() {
        return List.of(
                new MarketSnapshot("BTC", "Bitcoin", 67342.18, 3.24, 28_900_000_000.0, 1_330_000_000_000.0, "Bullish"),
                new MarketSnapshot("ETH", "Ethereum", 3516.45, 2.11, 18_450_000_000.0, 421_000_000_000.0, "Bullish"),
                new MarketSnapshot("SOL", "Solana", 158.42, 5.73, 5_500_000_000.0, 71_000_000_000.0, "Bullish"),
                new MarketSnapshot("XRP", "XRP", 0.61, -0.84, 2_980_000_000.0, 34_500_000_000.0, "Neutral"),
                new MarketSnapshot("ADA", "Cardano", 0.74, 1.18, 1_230_000_000.0, 26_000_000_000.0, "Bullish"),
                new MarketSnapshot("DOGE", "Dogecoin", 0.17, -1.62, 1_100_000_000.0, 24_000_000_000.0, "Neutral")
        );
    }
}
