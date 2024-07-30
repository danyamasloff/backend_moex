package ru.maslov.moexanalytic.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.maslov.moexanalytic.entity.Trade;
import ru.maslov.moexanalytic.service.TradeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    // Эндпоинт для получения всех сделок
    @GetMapping("/trades")
    public List<Trade> getAllTrades() {
        return tradeService.getAllTrades();
    }

    // Эндпоинт для получения сделки по ID
    @GetMapping("/trades/{id}")
    public Trade getTradeById(@PathVariable Long id) {
        return tradeService.getTradeById(id);
    }

    // Эндпоинт для создания новой сделки
    @PostMapping("/trades")
    public Trade createTrade(@RequestBody Trade trade) {
        return tradeService.saveTrade(trade);
    }

    // Эндпоинт для обновления существующей сделки
    @PutMapping("/trades/{id}")
    public Trade updateTrade(@PathVariable Long id, @RequestBody Trade tradeDetails) {
        return tradeService.updateTrade(id, tradeDetails);
    }

    // Эндпоинт для удаления сделки
    @DeleteMapping("/trades/{id}")
    public void deleteTrade(@PathVariable Long id) {
        tradeService.deleteTrade(id);
    }
}
