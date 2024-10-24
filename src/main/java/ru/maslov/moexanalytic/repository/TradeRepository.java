package ru.maslov.moexanalytic.repository;

import org.springframework.data.repository.query.Param;
import ru.maslov.moexanalytic.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long>{

    // Метод для проверки существования сделки по value, tradeno и quantity
    boolean existsByValueAndTradenoAndQuantity(BigDecimal value, Long tradeno, Integer quantity);

    @Query("SELECT t.secid, t.tradeDate, SUM(t.value) FROM Trade t GROUP BY t.secid, t.tradeDate")
    List<Object[]> findSumValueBySecidAndTradeDate();

    // Метод для получения сделок по secid и диапазону дат
    @Query("SELECT t FROM Trade t WHERE t.secid = :secid AND t.systime BETWEEN :startDate AND :endDate")
    List<Trade> findBySecidAndSystimeBetween(
            @Param("secid") String secid,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


    // Выборка всех уникальных secid
    @Query("SELECT DISTINCT t.secid FROM Trade t")
    List<String> findDistinctSecids();

    // Запрос для выборки сделок по secid и диапазону дат
    @Query("SELECT t FROM Trade t WHERE t.secid = :secid AND t.systime BETWEEN :startDateTime AND :endDateTime")
    List<Trade> findTradesBySecidAndDateRange(String secid, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
