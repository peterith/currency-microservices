package com.peterith.currencymicroservices.currencyconversionservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @HystrixCommand(fallbackMethod = "convertCurrencyFallback", commandProperties = {
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="10000"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="40")
    })
    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion convertCurrency(@PathVariable String from, @PathVariable String to,
                                              @PathVariable BigDecimal quantity) {

        CurrencyConversion response = currencyConversionService.getExchangeValue(from, to);

        return new CurrencyConversion(response.getId(), from, to, response.getConversionMultiple(), quantity,
                quantity.multiply(response.getConversionMultiple()), response.getPort());
    }

    @GetMapping("/currency-converter-fallback/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion convertCurrencyFallback(@PathVariable String from, @PathVariable String to,
                                                    @PathVariable BigDecimal quantity) {

        return new CurrencyConversion(404L, from, to, new BigDecimal(100), quantity,
                quantity.multiply(new BigDecimal(100)), 0);
    }
}
