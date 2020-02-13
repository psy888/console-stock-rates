package com.psy888.consolestockrates.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@DynamicUpdate
//@NamedEntityGraph(name = "GroupInfo.detail",
//        attributeNodes = @NamedAttributeNode("members"))
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate implements Serializable, Comparable<Rate> {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
    @Id
    @Column(insertable = false, updatable = false)
    private String symbol;
    private double latestPrice;
    private double changePercent;

//    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "symbol", referencedColumnName = "symbol")
    @OneToOne(fetch = FetchType.LAZY)
    private Company company;


//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLatestPrice() {
        return String.valueOf(latestPrice);
    }

    public void setLatestPrice(String latestPrice) {
        try {
            this.latestPrice = Double.parseDouble(latestPrice);
        } catch (Exception e) {
            this.latestPrice = 0;
        }
    }

    public double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(String changePercent) {
        try {
            this.changePercent = Double.parseDouble(changePercent);
        } catch (Exception e) {
            this.changePercent = 0;
        }
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Rate{" +
                " symbol='" + symbol + '\'' +
                ", \tcompany=" + company.getName() +
                ", \t\tlatestPrice=" + latestPrice +
                ", \t\tchangePercent=" + changePercent +
                '}';
    }

    @Override
    public int compareTo(Rate o) {
        return Double.compare(latestPrice, o.latestPrice);
    }
}