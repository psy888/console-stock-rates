package com.psy888.consolestockrates.service;

import com.psy888.consolestockrates.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class IexCloudCompanyService {


    private final RestTemplate restTemplate;
    private final String serverUrl;
    private final String token;

    public IexCloudCompanyService(@Autowired RestTemplate restTemplate,
                                  @Value("${application.server.url}") String serverUrl,
                                  @Value("${application.token}") String token) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
        this.token = token;
    }


    public List<Company> getData() {
        return restTemplate.exchange(
                serverUrl + "/ref-data/symbols?" +
                        "filter=symbol,name,isEnabled" +
                        "&token=" + token,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Company>>() {
                }
        ).getBody();
    }
}
