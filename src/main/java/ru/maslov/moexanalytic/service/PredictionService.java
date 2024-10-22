package ru.maslov.moexanalytic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maslov.moexanalytic.entity.AggregatedData;
import ru.maslov.moexanalytic.entity.Trade;
import ru.maslov.moexanalytic.repository.TradeRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.sql.Date;

@Service
public class PredictionService {

    @Autowired
    private TradeRepository tradeRepository;

    // Метод для агрегации данных по дням
    public List<AggregatedData> aggregateTradesByDay(String secid, Date startDate, Date endDate) {
        List<Trade> trades = tradeRepository.findBySecidAndTradeDateBetween(secid, startDate.toLocalDate(), endDate.toLocalDate());

        Map<LocalDate, List<Trade>> tradesByDate = trades.stream()
                .collect(Collectors.groupingBy(trade -> trade.getTradeDate().toLocalDate()));

        return tradesByDate.entrySet().stream().map(entry -> {
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
            aggregatedData.setSecid(dailyTrades.get(0).getSecid());

            return aggregatedData;
        }).collect(Collectors.toList());
    }

    // Метод для прогнозирования цены
    public double predictPrice(String secid, Date startDate, Date endDate) {
        List<AggregatedData> aggregatedDataList = aggregateTradesByDay(secid, startDate, endDate);

        if (aggregatedDataList.isEmpty()) {
            throw new IllegalArgumentException("No data available for the specified period.");
        }

        double[] x = new double[aggregatedDataList.size()];
        double[] y = new double[aggregatedDataList.size()];

        for (int i = 0; i < aggregatedDataList.size(); i++) {
            x[i] = aggregatedDataList.get(i).getTradeDate().getTime();
            y[i] = aggregatedDataList.get(i).getPrice().doubleValue();
        }

        // Пример линейной регрессии для прогнозирования
        double slope = 0.0;
        double intercept = 0.0;

        double xMean = 0.0;
        double yMean = 0.0;

        for (int i = 0; i < x.length; i++) {
            xMean += x[i];
            yMean += y[i];
        }
        xMean /= x.length;
        yMean /= y.length;

        double num = 0.0;
        double den = 0.0;

        for (int i = 0; i < x.length; i++) {
            num += (x[i] - xMean) * (y[i] - yMean);
            den += (x[i] - xMean) * (x[i] - xMean);
        }

        slope = num / den;
        intercept = yMean - slope * xMean;

        long nextDate = (long)(x[x.length - 1] + (x[1] - x[0])); // прогноз на следующий день
        return slope * nextDate + intercept;
    }
}
