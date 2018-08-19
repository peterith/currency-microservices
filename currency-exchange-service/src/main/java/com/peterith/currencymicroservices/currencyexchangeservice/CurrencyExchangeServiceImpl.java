package com.peterith.currencymicroservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final ExchangeValueRepository exchangeValueRepository;

    @Autowired
    public  CurrencyExchangeServiceImpl(ExchangeValueRepository exchangeValueRepository) {
        this.exchangeValueRepository = exchangeValueRepository;
    }

    @Override
    public Optional<ExchangeValue> findByFromAndTo(String from, String to) {
        return Optional.ofNullable(exchangeValueRepository.findByFromAndTo(from, to));
    }
}
