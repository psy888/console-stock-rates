package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedTransferQueue;
import java.util.stream.Collectors;

@Component
@EnableAsync
@EnableScheduling
public class RateRequestQueue {

    private Queue<CompletableFuture<Rate>> queue = new LinkedTransferQueue<>();

    @Autowired
    RateToDb rateToDb;
    @Autowired
    IexCloudRateService iexCloudRateService;

    public void addToQueue(String symbol) {
        CompletableFuture<Rate> completableFuture = iexCloudRateService.getData(symbol);
        completableFuture.thenAccept(rate -> rateToDb.saveToDb(rate));
        queue.add(completableFuture);

    }

    @Async
    public void updateAll() {
       /* int cnt = 0;
        while (!queue.isEmpty()) {
            try {
                queue.peek().get();
                Thread.sleep(10); //10 millis server
                if(cnt++>500) {
                    cnt=0;
                    Thread.sleep(5000);
                    System.out.println("Wait 5 seconds");
                    System.out.println(queue.size());

                }
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(queue.size());
                e.printStackTrace();
                break;
            }
            queue.poll();

        }
        */
        queue.forEach(this::loadData);
        System.out.println("started");
        queue.forEach(CompletableFuture::allOf);
        System.out.println("joined");

    }


    private void loadData(CompletableFuture rateCompletableFuture){
        try {
            rateCompletableFuture.get();
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            try {
                Thread.sleep(3000L);
                loadData(rateCompletableFuture);
            } catch (InterruptedException ee) {
                e.printStackTrace();
            }
        }
    }
}
