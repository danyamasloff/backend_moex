package ru.maslov.moexanalytic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.maslov.moexanalytic.repository.TradeRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SecidController {

    private final TradeRepository tradeRepository;

    @Autowired
    public SecidController(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @GetMapping("/secids")
    public List<String> getSecids() {
        return tradeRepository.findDistinctSecids().stream().collect(Collectors.toList());
    }
}
