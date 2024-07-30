package ru.maslov.moexanalytic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maslov.moexanalytic.service.TradeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AggregationController {

    @Autowired
    private TradeService tradeService;

    @GetMapping("/aggregated-trades")
    public List<Object[]> getAggregatedTrades() {
        return tradeService.aggregateTradesByDayAndInstrument();
    }
}
