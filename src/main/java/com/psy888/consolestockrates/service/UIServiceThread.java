package com.psy888.consolestockrates.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class UIServiceThread {
    @Autowired
    RateRepository rateRepository;
    @Autowired
    CompanyRepository companyRepository;

    private StringBuffer massage = new StringBuffer();

    @Scheduled(fixedRate = 5000L)
    void logChart() {
        if (rateRepository.count() > 5) {
            top5stocks();
            top5change();
        }
        if (massage.length() > 0) {

            System.out.println(massage.toString());

            massage.delete(0, massage.length());
        }
    }


    //The top 5 highest value stocks
    void top5stocks() {

    }

    //The most recent 5 companies that have the greatest change percent in stock value
    void top5change() {

    }

    void addMassage(String string) {
        massage.append(string);
        massage.append("\n");
    }

}
