package com.peterith.currencymicroservices.currencyexchangeservice;

import java.util.Optional;

public interface CurrencyExchangeService {

    Optional<ExchangeValue> findByFromAndTo(String from, String to);
}
