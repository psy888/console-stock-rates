package com.psy888.consolestockrates.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Company implements RestData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String symbol;

    private String name;
    private String isEnabled;


    public Company() {
    }

    /*
    "symbol":"A",
   "exchange":"NYS",
   "name":"ThcA inigeonIg.entelcslo ",
   "date":"2020-01-30",
   "type":"cs",
   "iexId":"IEX_46574843354B2D52",
   "region":"US",
   "currency":"USD",
   "isEnabled":true
     */

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }
}
