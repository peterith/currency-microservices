package com.peterith.currencymicroservices.currencyconversionservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyConversionControllerTests {

    @MockBean
    private CurrencyConversionService currencyConversionService;

    @Autowired
    private MockMvc mockMvc;

    private final String from = "EUR";
    private final String to = "INR";
    private final BigDecimal quantity = new BigDecimal(1000);

    @Test
    public void shouldReturnFallBackDataWhenCurrencyConversionNotFound() throws Exception {
        when(currencyConversionService.getExchangeValue(from, to)).thenReturn(Optional.empty());
        mockMvc.perform(get("/currency-converter/from/" + from + "/to/" + to + "/quantity/" + quantity))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("port").value(-1));
    }

    @Test
    public void shouldReturnCurrencyConversionFromService() throws Exception {
        CurrencyConversion currencyConversion = buildCurrencyConversion();
        when(currencyConversionService.getExchangeValue(from, to)).thenReturn(Optional.of(currencyConversion));
        mockMvc.perform(get("/currency-converter/from/" + from + "/to/" + to + "/quantity/" + quantity))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1000L))
                .andExpect(jsonPath("from").value(from))
                .andExpect(jsonPath("to").value(to))
                .andExpect(jsonPath("conversionMultiple").value(100.00))
                .andExpect(jsonPath("quantity").value(quantity))
                .andExpect(jsonPath("totalCalculatedAmount").value(new BigDecimal(100).multiply(quantity)))
                .andExpect(jsonPath("port").value(80));
    }

    private CurrencyConversion buildCurrencyConversion() {
        return CurrencyConversion.builder().id(1000L)
                .from(from)
                .to(to)
                .conversionMultiple(new BigDecimal(100))
                .quantity(new BigDecimal(1000))
                .totalCalculatedAmount(new BigDecimal(100).multiply(quantity))
                .port(80)
                .build();
    }
}
