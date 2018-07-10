package com.peterith.currencymicroservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, String> {
    ExchangeValue findByFromAndTo(String from, String to);
}
