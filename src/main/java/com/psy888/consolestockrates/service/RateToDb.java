package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RateToDb {
    @Autowired
    UIServiceThread uiServiceThread;
    @Autowired
    RateRepository rateRepository;


    public void saveToDb(Rate latestRemote) {
        Rate latestLocal = rateRepository.findBySymbol(latestRemote.getSymbol());
        if (latestLocal == null || latestLocal.compareTo(latestRemote) < 0) {
            if (latestLocal != null) {
                latestLocal.setChangePercent(String.valueOf(latestRemote.getChangePercent()));
                latestLocal.setLatestPrice(String.valueOf(latestRemote.getLatestPrice()));
                uiServiceThread.addMassage(latestRemote + " updated.");
            } else {
                rateRepository.save(latestRemote);
            }
        }
    }
}
