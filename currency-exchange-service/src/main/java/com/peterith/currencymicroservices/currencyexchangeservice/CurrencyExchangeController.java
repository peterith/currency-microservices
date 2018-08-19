package com.peterith.currencymicroservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;
    private final Environment environment;
    
    @Autowired
    public CurrencyExchangeController(CurrencyExchangeService currencyExchangeService, Environment environment) {
        this.currencyExchangeService = currencyExchangeService;
        this.environment = environment;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ResponseEntity getExchangeValue(@PathVariable String from, @PathVariable String to) {

        return currencyExchangeService.findByFromAndTo(from, to)
                .map(exchangeValue -> {
                    exchangeValue.setPort(Integer.parseInt(environment.getProperty("server.port")));
                    return ResponseEntity.ok(exchangeValue);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
