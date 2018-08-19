package com.peterith.currencymicroservices.currencyconversionservice;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface CurrencyConversionService {

    Optional<CurrencyConversion> getExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}
