package ru.maslov.moexanalytic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maslov.moexanalytic.entity.AggregatedData;
import ru.maslov.moexanalytic.entity.Trade;
import ru.maslov.moexanalytic.repository.AggregatedDataRepository;
import ru.maslov.moexanalytic.repository.TradeRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AggregationService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private AggregatedDataRepository aggregatedDataRepository;

    public List<AggregatedData> aggregateAndSaveTrades(String secid, LocalDate startDate, LocalDate endDate) {
        // Получение сделок из TradeRepository
        List<Trade> trades = tradeRepository.findBySecidAndTradeDateBetween(secid, Date.valueOf(startDate).toLocalDate(), Date.valueOf(endDate).toLocalDate());

        // Агрегация данных
        List<AggregatedData> aggregatedDataList = trades.stream()
                .collect(Collectors.groupingBy(trade -> trade.getTradeDate().toLocalDate()))
                .entrySet()
                .stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<Trade> dailyTrades = entry.getValue();

                    BigDecimal totalValue = dailyTrades.stream()
                            .map(Trade::getValue)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    int totalQuantity = dailyTrades.stream()
                            .mapToInt(Trade::getQuantity)
                            .sum();

                    BigDecimal averagePrice = totalValue.divide(BigDecimal.valueOf(totalQuantity), BigDecimal.ROUND_HALF_UP);

                    AggregatedData aggregatedData = new AggregatedData();
                    aggregatedData.setTradeDate(Date.valueOf(date));
                    aggregatedData.setPrice(averagePrice);
                    aggregatedData.setQuantity(totalQuantity);
                    aggregatedData.setSecid(secid);

                    // Сохранение в базу данных
                    aggregatedDataRepository.save(aggregatedData);

                    return aggregatedData;
                }).collect(Collectors.toList());

        return aggregatedDataList;
    }
}
