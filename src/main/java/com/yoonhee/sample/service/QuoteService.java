package com.yoonhee.sample.service;

import com.yoonhee.sample.model.Stock;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;

@Service
public class QuoteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteService.class);

    private ObjectMapper objectMapper;

    public QuoteService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Stock getQuote() {
        Stock stock = null;
        try {
            URL url = new URL("http://35.185.32.97/quote/AAPL");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String response = bufferedReader.readLine();

            LOGGER.info("Response from quote service: {}", response);

            stock = objectMapper.readValue(response, Stock.class);
            bufferedReader.close();

        } catch (IOException e) {
            LOGGER.error("Exception while processing quote", e);
            return null;
        }
        return stock;
    }
}
