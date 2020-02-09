package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class RateToDb {

    @Autowired
    RateRepository rateRepository;

    public void saveToDb(String symbol, Rate rate) {
        if (rateRepository.findTopBySymbolOrderByLatestPriceDesc(symbol) == null || rateRepository.findTopBySymbolOrderByLatestPriceDesc(symbol).compareTo(rate) < 1) {
            rateRepository.save(rate);
        }
    }
}
