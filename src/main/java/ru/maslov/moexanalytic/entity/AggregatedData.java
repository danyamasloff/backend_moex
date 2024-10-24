package ru.maslov.moexanalytic.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "aggregated_data")
public class AggregatedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String secid;

    // Заменяем tradeDate на systime, если нужно работать с временной меткой
    private LocalDateTime systime;

    private BigDecimal price; // Средняя цена за день
    private int quantity; // Общее количество сделок за день
    private BigDecimal value; // Суммарная стоимость всех сделок за день

    // Getters и Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecid() {
        return secid;
    }

    public void setSecid(String secid) {
        this.secid = secid;
    }

    public LocalDateTime getSystime() {
        return systime;
    }

    public void setSystime(LocalDateTime systime) {
        this.systime = systime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
