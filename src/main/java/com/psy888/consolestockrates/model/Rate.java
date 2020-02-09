package com.psy888.consolestockrates.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate implements RestData,Comparable<Rate> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String symbol;
    private String latestPrice;
    private String changePercent;
//    private String latestUpdate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(String latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(String changePercent) {
        this.changePercent = changePercent;
    }

//    public String getLatestUpdate() {
//        return latestUpdate;
//    }

//    public void setLatestUpdate(String latestUpdate) {
//        this.latestUpdate = latestUpdate;
//    }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", latestPrice='" + latestPrice + '\'' +
                ", changePercent='" + changePercent + '\'' +
//                ", latestUpdate='" + latestUpdate + '\'' +
                '}';
    }

    @Override
    public int compareTo(Rate o) {
        return this.latestPrice.compareTo(o.getLatestPrice());
    }
}