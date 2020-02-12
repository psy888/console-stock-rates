package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RateToDb {

    @Autowired
    RateRepository rateRepository;
    List<Rate> list = new LinkedList<>();


    public void saveToDb(Rate rate) {
        if (rateRepository.findTopBySymbolOrderByLatestPriceDesc(rate.getSymbol()) == null || rateRepository.findTopBySymbolOrderByLatestPriceDesc(rate.getSymbol()).compareTo(rate) != 0) {
            rateRepository.save(rate);
//            System.out.println(rate + " saved/updated ");
        }
    }
}
