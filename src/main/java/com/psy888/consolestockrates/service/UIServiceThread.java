package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class UIServiceThread implements Runnable{
    @Autowired
    RateRepository rateRepository;

    private StringBuffer massage = new StringBuffer();

//    @Scheduled(fixedRate = 5000L)
    void logChart() {
        if (rateRepository.count() > 5) {
            System.out.println("\n\n\n\n\n\n\n\n\n"); //spacer
            System.out.println("The top 5 highest value stocks");
            top5stocks();

            System.out.println("\n\nThe most recent 5 companies that have the greatest change percent in stock value");
            top5change();
        }
        if (massage.length() > 0) {

            System.out.println(massage.toString());

            massage.delete(0, massage.length());
        }
    }


    //The top 5 highest value stocks
    void top5stocks() {
        List<Rate> topStocks = rateRepository.findTop5ByOrderByLatestPriceDesc();

        System.out.println(topStocks.get(0));
        topStocks.stream()
                .skip(1)
                .sorted((o1, o2) ->
                        o1.getCompany().getName().compareTo(o2.getCompany().getName()))
                .forEach(System.out::println);

    }

    //The most recent 5 companies that have the greatest change percent in stock value
    void top5change() {
        rateRepository.findTop5ByOrderByChangePercentDesc()
                .forEach(System.out::println);
    }

    void addMassage(String string) {
        massage.append(string);
        massage.append("\n");
    }

    @Override
    public void run() {
        logChart();
    }
}
