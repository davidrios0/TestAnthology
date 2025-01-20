package com.documentproject.comparison_microservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RequestDataApi {
    @Autowired
    private RestTemplate restTemplate;

    public String getRequest(String path) {

        String url = "http://localhost:8080/" + path;

        try {

            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return "";
            }
        }
        catch (Exception e) {
            return "";

        }
    }

}
