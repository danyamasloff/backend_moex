package ru.maslov.moexanalytic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maslov.moexanalytic.entity.AggregatedData;



@Repository
public interface AggregatedDataRepository extends JpaRepository<AggregatedData, Long> {
    // Здесь можно добавить кастомные запросы для работы с AggregatedData
}

