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
    //    public static List<CompletableFuture<Rate>> requests = new ArrayList<>();
    //todo add semafore!
    @Autowired
    RateToDb rateToDb;
    @Autowired
    IexCloudRateService iexCloudRateService;
    //    @Qualifier("asyncExecutor")
//    @Autowired
//    Executor taskExecutor;

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
//            Future<Rate> rateFeature =  taskExecutor.submit(() -> iexCloudRateService.getData(symbol));
//        System.out.println(rateFeature.get().toString());
//            System.out.println("thread started " + this.getSymbol());

//            rateToDb.saveToDb(symbol, iexCloudRateService.getData(symbol));
            CompletableFuture<Rate> completableFuture = iexCloudRateService.getData(symbol);
            completableFuture.thenAccept(rate -> rateToDb.saveToDb(symbol, rate));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            completableFuture.join();
            completableFuture.get();

////            requests.add(completableFuture);
//            while (true){/
//                if(completableFuture.isDone()) {
//                    rateToDb.saveToDb(symbol, completableFuture.get());
//                    break;
//                }
//            }

//            CompletableFuture.allOf(completableFuture).join();
//            System.out.println("thread finished " + this.getSymbol());
        } catch (InterruptedException | ExecutionException ieEe) {
            ieEe.printStackTrace();
        }

    }
}
