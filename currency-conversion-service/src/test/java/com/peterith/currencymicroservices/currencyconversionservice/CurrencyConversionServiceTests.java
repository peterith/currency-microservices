package com.peterith.currencymicroservices.currencyconversionservice;

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
public class CurrencyConversionServiceTests {

    @MockBean
    private CurrencyExchangeClient currencyExchangeClient;

    @Autowired
    private CurrencyConversionService currencyConversionService;

    private final String from = "EUR";
    private final String to = "INR";
    private final BigDecimal quantity = new BigDecimal(1000);

    @Test
    public void shouldReturnEmptyOptionalWhenCurrencyConversionNotFound() {
        when(currencyExchangeClient.getExchangeValue(from, to)).thenReturn(null);
        assertThat(currencyConversionService.getExchangeValue(from, to)).isEmpty();
    }

    @Test
    public void shouldReturnCurrencyConversionFromFeignClient() {
        CurrencyConversion currencyConversion = buildCurrencyConversion();
        when(currencyExchangeClient.getExchangeValue(from, to)).thenReturn(currencyConversion);
        assertThat(currencyConversionService.getExchangeValue(from, to)).containsSame(currencyConversion);
    }

    private CurrencyConversion buildCurrencyConversion() {
        return CurrencyConversion.builder()
                .id(1000L)
                .from(from)
                .to(to)
                .conversionMultiple(new BigDecimal(100))
                .quantity(new BigDecimal(1000))
                .totalCalculatedAmount(new BigDecimal(100).multiply(quantity))
                .port(80)
                .build();
    }
}
