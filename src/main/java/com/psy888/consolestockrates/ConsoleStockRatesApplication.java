package com.psy888.consolestockrates;

import com.psy888.consolestockrates.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
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


    ExecutorService executor2;
    @Autowired
    RateUpdateThread rateUpdateThread;

    @Autowired
    RateRequestQueue rateRequestQueue;

    @Autowired
    UIServiceThread uiServiceThread;
    @Autowired
    ScheduledExecutorService scheduledExecutorService;


    public static void main(String[] args) {
        SpringApplication.run(ConsoleStockRatesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        executor2 = context.getBean("cachedExecutor", ExecutorService.class);

        System.out.println("Start company query");
        LocalTime t = LocalTime.now();
        companyToDb.saveToDb(iexCloudCompanyService.getData());
        System.out.println("Finish company query " + SECONDS.between(t, LocalTime.now()) + " seconds");
//
        System.out.println("Start rate query");
        updateRates();
        scheduledExecutorService.scheduleAtFixedRate(uiServiceThread, 5000L, 10000L, TimeUnit.MILLISECONDS);

        while (true) {
            if(!executor2.isTerminated()){
                wait(10L);
            }else {
                updateRates();
            }
        }

//        System.out.println("Finish");
    }

    private void updateRates(){
        companyRepository.findAllByIsEnabledContaining("true").forEach(company -> {
            executor2.execute(rateUpdateThread.setSymbol(company.getSymbol()));
        });
        executor2.shutdown();
    }
}
