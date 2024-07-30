package ru.maslov.moexanalytic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maslov.moexanalytic.service.MoexService;

@RestController
@RequestMapping("/api")
public class DataLoadController {

    @Autowired
    private MoexService moexService;

    @GetMapping("/load-trades")
    public String loadTrades() {
        moexService.saveTradesData();
        return "Trades data loaded successfully!";
    }
}
