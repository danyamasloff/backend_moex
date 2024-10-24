package ru.maslov.moexanalytic.service;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maslov.moexanalytic.entity.Trade;
import ru.maslov.moexanalytic.repository.TradeRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class PredictionService {

    @Autowired
    private TradeRepository tradeRepository;

    public double predictPrice(String secid, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // Получаем данные сделок по secid и диапазону дат
        List<Trade> trades = tradeRepository.findTradesBySecidAndDateRange(secid, startDateTime, endDateTime);

        if (trades.isEmpty()) {
            throw new IllegalArgumentException("No trade data available for the specified period.");
        }

        // Создаем модель линейной регрессии
        SimpleRegression regression = new SimpleRegression();

        // Добавляем данные в регрессию
        for (Trade trade : trades) {
            regression.addData(trade.getSystime().toEpochSecond(ZoneOffset.UTC), trade.getPrice().doubleValue());
        }

        // Прогнозируем цену на следующий день
        long nextTimestamp = trades.get(trades.size() - 1).getSystime().toEpochSecond(ZoneOffset.UTC) + 86400; // Следующий день
        return regression.predict(nextTimestamp);
    }
}
