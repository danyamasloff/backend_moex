package ru.maslov.moexanalytic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.maslov.moexanalytic.service.PredictionService;

import java.sql.Date;

@RestController
@RequestMapping("/api")
public class PredictionController {

    private final PredictionService predictionService;

    @Autowired
    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @GetMapping("/predict")
    public double predictPrice(@RequestParam String secid,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        // Вызов метода прогнозирования из PredictionService
        return predictionService.predictPrice(secid, startDate, endDate);
    }
}
