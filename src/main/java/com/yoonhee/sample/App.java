package com.yoonhee.sample;

import com.yoonhee.sample.model.Stock;
import com.yoonhee.sample.util.StockHelper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Write a java program which polls a simple stock quote API at 5 second intervals and which calculates the following for the stock AAPL
 * 1. Sysout change in market capitalization after 5 minutes upon exit the program
 * 2. Sysout the largest change in market capitalization in a 5 second interval
 */
public class App {

    public static BigDecimal initMarketCap = BigDecimal.ZERO;
    public static BigDecimal lastMarketCap = BigDecimal.ZERO;
    public static Stock prevStock = null;
    public static BigDecimal maxMarketCapDiff = BigDecimal.ZERO;

    public static void main(String[] args) {
        final String url = "http://35.185.32.97/quote/AAPL";
        System.out.println("Start AAPL data polling system");

        final Runnable runnable = new Runnable() {
            StockHelper helper = new StockHelper();
            @Override
            public void run() {
                Stock stock = getData(url, helper);

                BigDecimal currentDiff = prevStock==null? BigDecimal.ZERO : stock.getMarketCapitalization().subtract(prevStock.getMarketCapitalization());
                int cmp = maxMarketCapDiff.abs().compareTo(currentDiff.abs());
                maxMarketCapDiff = cmp > 0 ? maxMarketCapDiff : currentDiff;
                prevStock = stock;

                helper.printStatus();

            }
        };

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);

        try {
            scheduledExecutorService.awaitTermination(1, TimeUnit.MINUTES);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        scheduledExecutorService.shutdown();
        printMarketCapSummary();
    }


    private static Stock getData(String endPoint, StockHelper helper) {
        Stock stock = null;
        try {
            URL url = new URL(endPoint);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String response = bufferedReader.readLine();

            JSONObject jsonObject = new JSONObject(response);
            stock = helper.parseJson(jsonObject);
            if(!helper.isAAPLQuote(stock.getSymbol()))
                return null;

            System.out.println(stock.toString());
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stock;
    }

    private static void printMarketCapSummary(){
        System.out.println("===============================");
        System.out.println("Close AAPL data polling system");
        System.out.println("Change in Market Capitalization Summary");
        System.out.println("initial Market Capitalization: " + initMarketCap);
        System.out.println("Last Market Capitalization: " + lastMarketCap);
    }
}
