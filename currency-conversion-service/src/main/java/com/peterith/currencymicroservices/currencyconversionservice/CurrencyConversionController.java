package com.peterith.currencymicroservices.currencyconversionservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class CurrencyConversionController {

    private final CurrencyConversionService currencyConversionService;

    @Autowired
    public CurrencyConversionController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @HystrixCommand(fallbackMethod = "convertCurrencyFallback", commandProperties = {
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="10000"),
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="40")
    })
    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity convertCurrency(@PathVariable String from, @PathVariable String to,
                                          @PathVariable BigDecimal quantity) throws Exception {

        return currencyConversionService.getExchangeValue(from, to).map(currencyConversion -> {
            currencyConversion.setQuantity(quantity);
            currencyConversion.setTotalCalculatedAmount(
                    currencyConversion.getQuantity().multiply(currencyConversion.getConversionMultiple()));
            return ResponseEntity.ok(currencyConversion);
        })
                .orElseThrow(() -> new Exception());
    }

    @GetMapping("/currency-converter-fallback/from/{from}/to/{to}/quantity/{quantity}")
    public ResponseEntity convertCurrencyFallback(@PathVariable String from, @PathVariable String to,
                                                    @PathVariable BigDecimal quantity) {

        return ResponseEntity.ok(new CurrencyConversion(404L, from, to, new BigDecimal(100), quantity,
                quantity.multiply(new BigDecimal(100)), -1));
    }
}
