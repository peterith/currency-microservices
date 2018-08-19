package com.peterith.currencymicroservices.currencyexchangeservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyExchangeServiceTests {

    @MockBean
    private ExchangeValueRepository exchangeValueRepository;

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    private final String from = "EUR";
    private final String to = "INR";

    @Test
    public void shouldReturnEmptyOptionalWhenExchangeValueNotFound() {
        when(exchangeValueRepository.findByFromAndTo(from, to)).thenReturn(null);
        assertThat(currencyExchangeService.findByFromAndTo(from, to)).isEmpty();
    }

    @Test
    public void shouldReturnExchangeValueFromRepository() {
        ExchangeValue exchangeValue = buildExchangeValue();
        when(exchangeValueRepository.findByFromAndTo(from, to)).thenReturn(exchangeValue);
        assertThat(currencyExchangeService.findByFromAndTo(from, to)).containsSame(exchangeValue);
    }

    private ExchangeValue buildExchangeValue() {
        return ExchangeValue.builder().id(1000L)
                .from(from)
                .to(to)
                .conversionMultiple(new BigDecimal(100))
                .port(80)
                .build();
    }
}
