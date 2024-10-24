package ru.maslov.moexanalytic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maslov.moexanalytic.repository.AggregatedDataRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AggregationService {

    @Autowired
    private AggregatedDataRepository aggregatedDataRepository;

    public List<Object[]> getAllAggregatedTrades() {
        // SQL-запрос для агрегации данных по всем SECID за все время
        return aggregatedDataRepository.findAllAggregatedTrades();
    }
}
