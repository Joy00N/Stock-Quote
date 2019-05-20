package com.yoonhee.sample.service;

import com.yoonhee.sample.model.Stock;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
public class MarketCapAnalyzeServiceTest {

    private static final EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();

    private MarketCapAnalyzeService marketCapAnalyzeService;

    private QuoteService quoteService;

    @Before
    public void before() {
        quoteService = mock(QuoteService.class);
        marketCapAnalyzeService = new MarketCapAnalyzeService(new ObjectMapper(), quoteService);
    }

    @Test
    public void getData_WrongSymbol() {
        Stock testData = generateRandomStock();
        testData.setSymbol("NON_AAPL");

        when(quoteService.getQuote()).thenReturn(testData);

        Stock actual = marketCapAnalyzeService.getData();
        assertNull(actual);
    }

    private Stock generateRandomStock() {
        Stock stock = enhancedRandom.random(Stock.class);
        return stock;
    }
}
