package com.peterith.currencymicroservices.currencyexchangeservice;

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
public class CurrencyExchangeControllerTests {

	@MockBean
	private CurrencyExchangeService currencyExchangeService;

	@Autowired
	private MockMvc mockMvc;

	private final String from = "EUR";
	private final String to = "INR";

	@Test
	public void shouldReturnNotFoundStatusWhenExchangeValueNotFound() throws Exception {
		when(currencyExchangeService.findByFromAndTo(from, to)).thenReturn(Optional.empty());
		mockMvc.perform(get("/currency-exchange/from/" + from + "/to/" + to))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void shouldReturnExchangeValueWrappedInData() throws Exception {
		ExchangeValue exchangeValue = buildExchangeValue();
		when(currencyExchangeService.findByFromAndTo(from, to)).thenReturn(Optional.of(exchangeValue));
		mockMvc.perform(get("/currency-exchange/from/" + from + "/to/" + to))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(1000L))
				.andExpect(jsonPath("from").value(from))
				.andExpect(jsonPath("to").value(to))
				.andExpect(jsonPath("conversionMultiple").value(100.00))
				.andExpect(jsonPath("port").value(80));
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
