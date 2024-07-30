package ru.maslov.moexanalytic.service;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictionService {

    public double predictPrice(List<Double> prices) {
        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < prices.size(); i++) {
            regression.addData(i, prices.get(i));
        }
        return regression.predict(prices.size());
    }
}