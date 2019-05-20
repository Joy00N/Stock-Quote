package com.yoonhee.sample.service;

import com.yoonhee.sample.model.Stock;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;

@Service
public class MarketCapAnalyzeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MarketCapAnalyzeService.class);
    private static BigDecimal initMarketCap = BigDecimal.ZERO;
    private static BigDecimal lastMarketCap = BigDecimal.ZERO;
    public static BigDecimal maxMarketCapDiff = BigDecimal.ZERO;
    public static Stock prevStock = null;
    private static boolean isInitial = true;

    private ObjectMapper objectMapper;
    private QuoteService quoteService;

    public MarketCapAnalyzeService(ObjectMapper objectMapper, QuoteService quoteService) {
        this.objectMapper = objectMapper;
        this.quoteService = quoteService;
    }

    public Stock getData() {
        Stock stock = quoteService.getQuote();
        if(stock == null) {
            LOGGER.warn("Stock quote is null");
            return null;
        }
        if (!this.isAAPLQuote(stock.getSymbol())) {
            LOGGER.warn("Stock quote is not for AAPL");
            return null;
        }
        populateMarkeyCapSummary(stock);
        System.out.println(stock.toString());
        return stock;
    }

    public BigDecimal calculateMaxMarketCapDiff(Stock stock, Stock prevStock, BigDecimal maxMarketCapDiff) {
        BigDecimal currentDiff = prevStock == null ? BigDecimal.ZERO : this.getMarketCapitalization(stock).subtract(this.getMarketCapitalization(prevStock));
        return maxMarketCapDiff.abs().compareTo(currentDiff.abs()) > 0 ? maxMarketCapDiff : currentDiff;
    }

    private BigDecimal getMarketCapitalization(Stock stock) {
        return stock.getSharesOutstanding().multiply(stock.getRegularMarketPrice());
    }

    private boolean isAAPLQuote(String symbol) {
        return symbol.equals("AAPL");
    }

    private void populateMarkeyCapSummary(Stock stock) {
        if (isInitial) {
            initMarketCap = this.getMarketCapitalization(stock);
            isInitial = false;
        }
        lastMarketCap = this.getMarketCapitalization(stock);
    }

    private void printMarketCapSummary() {
        System.out.println("Close AAPL data polling system");
        System.out.println("Change in Market Capitalization Summary");
        System.out.println("initial Market Capitalization: " + initMarketCap);
        System.out.println("Last Market Capitalization: " + lastMarketCap);
    }
}
