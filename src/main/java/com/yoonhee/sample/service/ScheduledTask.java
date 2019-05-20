package com.yoonhee.sample.service;

import com.yoonhee.sample.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

import static com.yoonhee.sample.service.MarketCapAnalyzeService.maxMarketCapDiff;
import static com.yoonhee.sample.service.MarketCapAnalyzeService.prevStock;

@Component
public class ScheduledTask extends ThreadPoolTaskExecutor {
    @Autowired
    private MarketCapAnalyzeService marketCapAnalyzeService;

    @Scheduled(fixedRate = 1000)
    public void reportMarketCap() {
        Stock stock = marketCapAnalyzeService.getData();

        if (stock != null) {
            maxMarketCapDiff = marketCapAnalyzeService.calculateMaxMarketCapDiff(stock, prevStock, maxMarketCapDiff);
            prevStock = stock;

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("Maximum Change in Market Capitalization: " + maxMarketCapDiff);
            System.out.println(timestamp);


        }
    }


}
