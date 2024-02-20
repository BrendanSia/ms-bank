package com.bank.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ThirdPartyApiService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${thirdparty.api.url}")
    private String apiUrl;

    public String callThirdPartyApi() {
        String response = restTemplate.getForObject(apiUrl, String.class);
        return response;
    }
}
