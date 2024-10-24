package ru.maslov.moexanalytic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.maslov.moexanalytic.service.PredictionService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @GetMapping("/predict-price") // Обрати внимание на название эндпоинта
    public double predictPrice(@RequestParam String secid,
                               @RequestParam String startDate,
                               @RequestParam String endDate) {

        // Преобразование строки в LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T00:00:00", formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T23:59:59", formatter);

        return predictionService.predictPrice(secid, startDateTime, endDateTime);
}
}
