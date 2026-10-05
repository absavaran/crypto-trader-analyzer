package com.crypto.trader.controller;

import com.crypto.trader.model.MarketSnapshot;
import com.crypto.trader.service.MarketSnapshotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MarketSnapshotController {

    private final MarketSnapshotService marketSnapshotService;

    public MarketSnapshotController(MarketSnapshotService marketSnapshotService) {
        this.marketSnapshotService = marketSnapshotService;
    }

    @GetMapping("/market")
    public List<MarketSnapshot> getMarket() {
        return marketSnapshotService.getSnapshots();
    }
}
