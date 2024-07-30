package ru.maslov.moexanalytic.controller;

import ru.maslov.moexanalytic.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @PostMapping("/predict")
    public double predictPrice(@RequestBody List<Double> prices) {
        return predictionService.predictPrice(prices);
    }
}