package ru.maslov.moexanalytic.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Trade {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long tradeno;
    private LocalTime tradetime;
    private String boardid;
    private String secid;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal value;
    private String period;
    private Integer tradetimeGrp;
    private LocalDateTime systime;
    private String buysell;
    private Integer decimals;
    private String tradingsession;
    private Date tradeDate;


    // Getters and Setters
    public Long getTradeno() {
        return tradeno;
    }

    public void setTradeno(Long tradeno) {
        this.tradeno = tradeno;
    }

    public LocalTime getTradetime() {
        return tradetime;
    }

    public void setTradetime(LocalTime tradetime) {
        this.tradetime = tradetime;
    }

    public String getBoardid() {
        return boardid;
    }

    public void setBoardid(String boardid) {
        this.boardid = boardid;
    }

    public String getSecid() {
        return secid;
    }

    public void setSecid(String secid) {
        this.secid = secid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getTradetimeGrp() {
        return tradetimeGrp;
    }

    public void setTradetimeGrp(Integer tradetimeGrp) {
        this.tradetimeGrp = tradetimeGrp;
    }

    public LocalDateTime getSystime() {
        return systime;
    }

    public void setSystime(LocalDateTime systime) {
        this.systime = systime;
    }

    public String getBuysell() {
        return buysell;
    }

    public void setBuysell(String buysell) {
        this.buysell = buysell;
    }

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    public String getTradingsession() {
        return tradingsession;
    }

    public void setTradingsession(String tradingsession) {
        this.tradingsession = tradingsession;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}