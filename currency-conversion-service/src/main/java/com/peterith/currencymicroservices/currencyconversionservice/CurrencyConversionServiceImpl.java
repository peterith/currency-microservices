package com.peterith.currencymicroservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final CurrencyExchangeClient currencyExchangeClient;

    @Autowired
    public CurrencyConversionServiceImpl(CurrencyExchangeClient currencyExchangeClient) {
        this.currencyExchangeClient = currencyExchangeClient;
    }

    @Override
    public Optional<CurrencyConversion> getExchangeValue(String from, String to) {
        return Optional.ofNullable(currencyExchangeClient.getExchangeValue(from, to));
    }
}
