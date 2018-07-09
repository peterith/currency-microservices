package com.peterith.microservicesdemo.currencyexchangeservices;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, String> {
    ExchangeValue findByFromAndTo(String from, String to);
}
