package com.peterith.currencymicroservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    private final CurrencyExchangeService currencyExchangeService;

    @Autowired
    public CurrencyExchangeController(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ResponseEntity getExchangeValue(@PathVariable String from, @PathVariable String to) {

        return currencyExchangeService.findByFromAndTo(from, to)
                .map(exchangeValue -> ResponseEntity.ok(exchangeValue))
                .orElse(ResponseEntity.notFound().build());
    }
}
