package com.documentproject.comparison_microservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RequestDataApi {
    @Autowired
    private RestTemplate restTemplate;

    public String getRequest(String path) {

        String url = "http://localhost:8080/" + path;

        return restTemplate.getForObject(url, String.class);
    }
}
