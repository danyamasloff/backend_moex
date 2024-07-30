package ru.maslov.moexanalytic.repository;

import ru.maslov.moexanalytic.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

    boolean existsByValueAndTradenoAndQuantity(BigDecimal value, Long tradeno, Integer quantity);

    @Query("SELECT t.secid, t.tradetimeGrp, SUM(t.value) FROM Trade t GROUP BY t.secid, t.tradetimeGrp")

    List<Object[]> findSumValueByInstrumentAndTradeDate();
}
