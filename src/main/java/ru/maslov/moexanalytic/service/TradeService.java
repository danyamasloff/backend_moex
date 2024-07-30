package ru.maslov.moexanalytic.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maslov.moexanalytic.entity.Trade;
import ru.maslov.moexanalytic.repository.TradeRepository;

import java.util.List;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    // Метод для получения всех сделок
    public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    //Метод для получения агрегированных данных.
    public List<Object[]> aggregateTradesByDayAndInstrument() {
        return tradeRepository.findSumValueByInstrumentAndTradeDate();
    }

    // Метод для получения сделки по ID
    public Trade getTradeById(Long id) {
        return tradeRepository.findById(id).orElse(null);
    }

    // Метод для сохранения новой сделки
    public Trade saveTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    // Метод для обновления существующей сделки
    public Trade updateTrade(Long id, Trade tradeDetails) {
        Trade trade = tradeRepository.findById(id).orElse(null);
        if (trade != null) {
            trade.setSecid(tradeDetails.getSecid());
            trade.setPrice(tradeDetails.getPrice());
            trade.setValue(tradeDetails.getValue());
            trade.setTradetime(tradeDetails.getTradetime());
            return tradeRepository.save(trade);
        }
        return null;
    }

    // Метод для удаления сделки
    public void deleteTrade(Long id) {
        tradeRepository.deleteById(id);
    }
}