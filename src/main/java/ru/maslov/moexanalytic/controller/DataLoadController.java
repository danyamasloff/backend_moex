package ru.maslov.moexanalytic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maslov.moexanalytic.entity.Trade;
import ru.maslov.moexanalytic.service.MoexService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/data")
public class DataLoadController {

    @Autowired
    private MoexService moexService;

    // Метод для загрузки и сохранения данных
    @PostMapping("/load-trades")
    public ResponseEntity<String> loadAndSaveTrades(@RequestBody List<Trade> trades) {
        moexService.saveTradesData(trades);
        return ResponseEntity.ok("Trades data saved successfully");
    }

    // Метод для получения данных сделок с MOEX API
    @GetMapping("/fetch-trades")
    public ResponseEntity<List<Trade>> fetchTradesData(
            @RequestParam String secid,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<Trade> trades = moexService.fetchTradesData(secid, start, end);
        return ResponseEntity.ok(trades);
    }
}
