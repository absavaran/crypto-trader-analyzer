package com.crypto.trader.service;

import com.crypto.trader.model.MarketSnapshot;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class MarketSnapshotService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<MarketSnapshot> getSnapshots() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=bitcoin,ethereum,solana,ripple,cardano,dogecoin&price_change_percentage=24h&sparkline=false"))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                JsonNode root = objectMapper.readTree(response.body());
                List<MarketSnapshot> snapshots = new ArrayList<>();

                for (JsonNode node : root) {
                    String symbol = node.path("symbol").asText("").toUpperCase();
                    String name = node.path("name").asText("Unknown");
                    double price = node.path("current_price").asDouble(0.0);
                    double change24h = node.path("price_change_percentage_24h").asDouble(0.0);
                    double volume24h = node.path("total_volume").asDouble(0.0);
                    double marketCap = node.path("market_cap").asDouble(0.0);
                    String trend = change24h >= 0 ? "Bullish" : "Bearish";

                    snapshots.add(new MarketSnapshot(symbol, name, price, change24h, volume24h, marketCap, trend));
                }

                if (!snapshots.isEmpty()) {
                    return snapshots;
                }
            }
        } catch (Exception e) {
            // Fallback to static reference data if external API is unavailable.
        }

        return fallbackSnapshots();
    }

    private List<MarketSnapshot> fallbackSnapshots() {
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
