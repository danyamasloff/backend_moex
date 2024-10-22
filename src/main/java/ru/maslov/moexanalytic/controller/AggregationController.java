package ru.maslov.moexanalytic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maslov.moexanalytic.entity.AggregatedData;
import ru.maslov.moexanalytic.service.AggregationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AggregationController {

    @Autowired
    private AggregationService aggregationService;

    @GetMapping("/aggregated-trades")
    public ResponseEntity<List<AggregatedData>> getAggregatedTrades(
            @RequestParam String secid,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<AggregatedData> aggregatedDataList = aggregationService.aggregateAndSaveTrades(secid, startDate, endDate);
        return ResponseEntity.ok(aggregatedDataList);
    }
}
