package com.peterith.currencymicroservices.currencyexchangeservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CurrencyExchangeServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeServicesApplication.class, args);
	}
}