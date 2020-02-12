package com.psy888.consolestockrates;

import com.psy888.consolestockrates.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;

@SpringBootApplication
public class ConsoleStockRatesApplication implements CommandLineRunner {
    @Autowired
    IexCloudCompanyService iexCloudCompanyService;
    @Autowired
    CompanyToDb companyToDb;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    ApplicationContext context;


    ExecutorService executor2;
    @Autowired
    RateUpdateThread rateUpdateThread;

    @Autowired
    RateRequestQueue rateRequestQueue;

    public static void main(String[] args) {
        SpringApplication.run(ConsoleStockRatesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        executor2 = context.getBean("cachedExecutor", ExecutorService.class);

//        System.out.println("Start company query");
//        LocalTime t = LocalTime.now();
        companyToDb.saveToDb(iexCloudCompanyService.getData());
//        System.out.println("Finish company query " + SECONDS.between(t, LocalTime.now()) + " seconds");
//
//        System.out.println("Start rate query");
        companyRepository.findAllByIsEnabledContaining("true").forEach(company -> {
//           rateRequestQueue.addToQueue(company.getSymbol());
            executor2.execute(rateUpdateThread.setSymbol(company.getSymbol()));
//            taskExecutor.scheduleAtFixedRate(context.getBean(RateUpdateThread.class).setSymbol(company.getSymbol()),0,10L, TimeUnit.MILLISECONDS);
//            CompletableFuture<Void> future =  CompletableFuture.runAsync(context.getBean(RateUpdateThread.class).setSymbol(company.getSymbol()), taskExecutor);
        });
//        rateRequestQueue.updateAll();



//        System.out.println(taskExecutor.getActiveCount() + " Active threads");
        System.out.println("Finish");
//        System.out.println(taskExecutor.getThreadPoolExecutor().getCompletedTaskCount() + " CompletedTaskCount");
//
    }
}
