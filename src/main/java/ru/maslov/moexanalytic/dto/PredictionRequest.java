package ru.maslov.moexanalytic.dto;

import java.util.Date;
import java.util.List;

public class PredictionRequest {

    private List<Double> prices;
    private Date start;
    private Date end;

    // Getters and Setters
    public List<Double> getPrices() {
        return prices;
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
