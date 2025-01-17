package com.documentproject.comparison_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ComparisonMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComparisonMicroserviceApplication.class, args);
	}

}
