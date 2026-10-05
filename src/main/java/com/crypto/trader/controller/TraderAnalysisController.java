package com.crypto.trader.controller;

import com.crypto.trader.model.TraderAnalysisRequest;
import com.crypto.trader.model.TraderAnalysisResponse;
import com.crypto.trader.service.TraderAnalysisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TraderAnalysisController {

    private final TraderAnalysisService analysisService;

    public TraderAnalysisController(TraderAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping("/analyze")
    public TraderAnalysisResponse analyze(@RequestBody TraderAnalysisRequest request) {
        return analysisService.analyze(request);
    }

    @GetMapping("/health")
    public String health() {
        return "Crypto Trader Analyzer is running";
    }
}
