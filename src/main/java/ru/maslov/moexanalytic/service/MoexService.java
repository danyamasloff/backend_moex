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
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoexService {

    private static final String MOEX_API_URL = "https://iss.moex.com/iss/engines/stock/markets/shares/trades.json";

    @Autowired
    private TradeRepository tradeRepository;

    public List<Trade> fetchTradesData(String secid, LocalDate startDate, LocalDate endDate) {
        RestTemplate restTemplate = new RestTemplate();
        List<Trade> trades = new ArrayList<>();

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            String url = MOEX_API_URL + "?secid=" + secid + "&date=" + date.toString();
            String data = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(data);
                JsonNode tradesNode = root.path("trades").path("data");
                for (JsonNode tradeNode : tradesNode) {
                    Trade trade = new Trade();
                    trade.setTradeno(tradeNode.get(0).asLong());
                    trade.setTradetime(LocalTime.parse(tradeNode.get(1).asText(), DateTimeFormatter.ofPattern("HH:mm:ss")));
                    trade.setBoardid(tradeNode.get(2).asText());
                    trade.setSecid(tradeNode.get(3).asText());
                    trade.setPrice(new BigDecimal(tradeNode.get(4).asText()));
                    trade.setQuantity(tradeNode.get(5).asInt());
                    trade.setValue(new BigDecimal(tradeNode.get(6).asText()));
                    trade.setPeriod(tradeNode.get(7).asText());
                    trade.setTradetimeGrp(tradeNode.get(8).asInt());
                    trade.setSystime(LocalDateTime.parse(tradeNode.get(9).asText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    trade.setBuysell(tradeNode.get(10).asText());
                    trade.setDecimals(tradeNode.get(11).asInt());
                    trade.setTradingsession(tradeNode.get(12).asText());
                    trade.setTradeDate(Date.valueOf(date));

                    trades.add(trade);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return trades;
    }

    public void saveTradesData(List<Trade> trades) {
        for (Trade trade : trades) {
            if (!tradeRepository.existsByValueAndTradenoAndQuantity(trade.getValue(), trade.getTradeno(), trade.getQuantity())) {
                tradeRepository.save(trade);
            }
        }
    }
}
