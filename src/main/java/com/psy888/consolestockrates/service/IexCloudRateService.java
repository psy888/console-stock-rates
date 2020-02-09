package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Rate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Scope("prototype")
public class IexCloudRateService {

    private final RestTemplate restTemplate;
    private final String serverUrl;
    private final String token;

    public IexCloudRateService(RestTemplate restTemplate,
                               @Value("${application.server.url}") String serverUrl,
                               @Value("${application.token}") String token) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
        this.token = token;
    }
//    @Scheduled(fixedRate = 10L)
    public CompletableFuture<Rate> getData(String symbols) {
//    public Rate getData(String symbols) {
        return  CompletableFuture.supplyAsync(()->restTemplate.exchange(
//        return  restTemplate.exchange(
                serverUrl + "/stock/" + symbols + "/quote" +
                        "?filter=symbol,latestPrice,changePercent"+
                        "&token=" + token,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Rate>() {
                }
        ).getBody());
    }
}
