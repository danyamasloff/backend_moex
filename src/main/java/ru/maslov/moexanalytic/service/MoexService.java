package ru.maslov.moexanalytic.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.maslov.moexanalytic.entity.Trade;
import ru.maslov.moexanalytic.repository.TradeRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class MoexService {

    private static final String MOEX_API_URL = "https://iss.moex.com/iss/engines/stock/markets/shares/trades.json";

    @Autowired
    private TradeRepository tradeRepository;

    public String getTradesData() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(MOEX_API_URL, String.class);
    }

    public void saveTradesData() {
        String data = getTradesData();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(data);
            JsonNode trades = root.path("trades").path("data");
            for (JsonNode tradeNode : trades) {
                Long tradeno = tradeNode.get(0).asLong();
                LocalTime tradetime = LocalTime.parse(tradeNode.get(1).asText(), DateTimeFormatter.ofPattern("HH:mm:ss"));
                String boardid = tradeNode.get(2).asText();
                String secid = tradeNode.get(3).asText();
                BigDecimal price = new BigDecimal(tradeNode.get(4).asText());
                Integer quantity = tradeNode.get(5).asInt();
                BigDecimal value = new BigDecimal(tradeNode.get(6).asText());
                String period = tradeNode.get(7).asText();
                Integer tradetimeGrp = tradeNode.get(8).asInt();
                LocalDateTime systime = LocalDateTime.parse(tradeNode.get(9).asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String buysell = tradeNode.get(10).asText();
                Integer decimals = tradeNode.get(11).asInt();
                String tradingsession = tradeNode.get(12).asText();

                if (!tradeRepository.existsByValueAndTradenoAndQuantity(value, tradeno, quantity)) {
                    Trade trade = new Trade();
                    trade.setTradeno(tradeno);
                    trade.setTradetime(tradetime);
                    trade.setBoardid(boardid);
                    trade.setSecid(secid);
                    trade.setPrice(price);
                    trade.setQuantity(quantity);
                    trade.setValue(value);
                    trade.setPeriod(period);
                    trade.setTradetimeGrp(tradetimeGrp);
                    trade.setSystime(systime);
                    trade.setBuysell(buysell);
                    trade.setDecimals(decimals);
                    trade.setTradingsession(tradingsession);
                    tradeRepository.save(trade);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
