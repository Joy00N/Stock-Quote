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
    public static BigDecimal initMarketCap = BigDecimal.ZERO;
    public static BigDecimal lastMarketCap = BigDecimal.ZERO;
    public static BigDecimal maxMarketCapDiff = BigDecimal.ZERO;
    public static Stock prevStock = null;

    @Autowired
    private ObjectMapper objectMapper;

    public Stock getData(String endPoint) {
        Stock stock = null;
        try {
            URL url = new URL(endPoint);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String response = bufferedReader.readLine();

            stock = objectMapper.readValue(response, Stock.class);
            bufferedReader.close();

            if (!this.isAAPLQuote(stock.getSymbol())) {
                LOGGER.info("Stock quote is not for AAPL");
                return null;
            }
            //TODO delete
            System.out.println(stock.toString());

        } catch (IOException e) {
            LOGGER.info("File Not Found");
        }


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

    private void printMarketCapSummary() {
        System.out.println("===============================");
        System.out.println("Close AAPL data polling system");
        System.out.println("Change in Market Capitalization Summary");
        System.out.println("initial Market Capitalization: " + initMarketCap);
        System.out.println("Last Market Capitalization: " + lastMarketCap);
    }
}
