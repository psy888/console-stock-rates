package com.psy888.consolestockrates;

import com.psy888.consolestockrates.model.Rate;
import com.psy888.consolestockrates.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.SECONDS;

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
//    @Autowired
//    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    RateRequestQueue rateRequestQueue;

    public static void main(String[] args) {
        SpringApplication.run(ConsoleStockRatesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        System.out.println("Start company query");
        LocalTime t = LocalTime.now();
        companyToDb.saveToDb(iexCloudCompanyService.getData());
        System.out.println("Finish company query " + SECONDS.between(t, LocalTime.now()) + " seconds");

        System.out.println("Start rate query");

        companyRepository.findAllByIsEnabledContaining("true").forEach(company -> {
           rateRequestQueue.addToQueue(context.getBean(RateUpdateThread.class).setSymbol(company.getSymbol()));
//            taskExecutor.scheduleAtFixedRate(context.getBean(RateUpdateThread.class).setSymbol(company.getSymbol()),0,10L, TimeUnit.MILLISECONDS);
//            CompletableFuture<Void> future =  CompletableFuture.runAsync(context.getBean(RateUpdateThread.class).setSymbol(company.getSymbol()), taskExecutor);
        });
        rateRequestQueue.updateAll();



        System.out.println("Finish");
//
    }
}
