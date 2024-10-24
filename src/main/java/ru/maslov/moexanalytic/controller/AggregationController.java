package ru.maslov.moexanalytic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maslov.moexanalytic.service.AggregationService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AggregationController {

    @Autowired
    private AggregationService aggregationService;

    @GetMapping("/aggregated-trades")
    public ResponseEntity<List<Object[]>> getAllAggregatedTrades() {
        List<Object[]> aggregatedTrades = aggregationService.getAllAggregatedTrades();
        return ResponseEntity.ok(aggregatedTrades);
    }
}
