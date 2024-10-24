package ru.maslov.moexanalytic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.maslov.moexanalytic.entity.AggregatedData;

import java.sql.Date;
import java.util.List;

@Repository
public interface AggregatedDataRepository extends JpaRepository<AggregatedData, Long> {

    // Метод для выборки всех агрегированных данных
    @Query("SELECT t.secid, DATE(t.systime), SUM(t.price * t.quantity) as totalValue " +
            "FROM Trade t " +
            "GROUP BY t.secid, DATE(t.systime)")
    List<Object[]> findAllAggregatedTrades();

    // Запрос для выборки данных по secid и диапазону дат
    @Query("SELECT a FROM AggregatedData a WHERE a.secid = :secid AND a.systime BETWEEN :startDate AND :endDate")
    List<AggregatedData> findAggregatedDataBySecidAndDateRange(String secid, Date startDate, Date endDate);
}
