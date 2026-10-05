package com.crypto.trader.controller;

import com.crypto.trader.model.StrategySignal;
import com.crypto.trader.service.SignalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SignalController {

    private final SignalService signalService;

    public SignalController(SignalService signalService) {
        this.signalService = signalService;
    }

    @GetMapping("/signals")
    public List<StrategySignal> getSignals() {
        return signalService.getSignals();
    }
}
