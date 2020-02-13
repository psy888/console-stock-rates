package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Rate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Scope("prototype")

public class IexCloudRateService {

    @Qualifier("restTemplate")
    private final RestTemplate restTemplate;
    private final ThreadPoolTaskExecutor taskExecutor;
    private final String serverUrl;
    private final String token;

    public IexCloudRateService(RestTemplate restTemplate,
                               @Value("${application.server.url}") String serverUrl,
                               @Value("${application.token}") String token,
                               @Qualifier("taskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
        this.token = token;
        this.taskExecutor = taskExecutor;
    }

    public CompletableFuture<Rate> getData(String symbols) {
        return CompletableFuture.supplyAsync(() -> restTemplate.exchange(
                serverUrl + "/stock/" + symbols + "/quote" +
                        "?filter=symbol,latestPrice,changePercent" +
                        "&token=" + token,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Rate>() {
                }
        ).getBody(), taskExecutor);
    }
}
