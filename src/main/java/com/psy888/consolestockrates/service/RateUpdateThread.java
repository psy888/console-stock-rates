package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Scope("prototype")
public class RateUpdateThread implements Runnable {

    @Autowired
    RateToDb rateToDb;
    @Autowired
    IexCloudRateService iexCloudRateService;

    private String symbol;


    public String getSymbol() {
        return symbol;
    }

    public RateUpdateThread setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    @Override
    public void run() {
        try {
            CompletableFuture<Rate> completableFuture = iexCloudRateService.getData(symbol);
            completableFuture.thenAccept(rate -> rateToDb.saveToDb(rate));
            completableFuture.get();
        } catch (InterruptedException | ExecutionException ieEe) {
            ieEe.printStackTrace();
        }

    }
}

