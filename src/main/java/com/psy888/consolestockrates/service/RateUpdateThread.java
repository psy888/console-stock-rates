package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Service
@Scope("prototype")
public class RateUpdateThread implements Runnable {
    //    public static List<CompletableFuture<Rate>> requests = new ArrayList<>();
    //todo add semafore!
    @Autowired
    RateToDb rateToDb;
    @Autowired
    IexCloudRateService iexCloudRateService;


    private String symbol;

    public RateUpdateThread() {
    }

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
