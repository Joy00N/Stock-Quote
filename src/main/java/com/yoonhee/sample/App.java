package com.yoonhee.sample;

import com.yoonhee.sample.model.Stock;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        final String url = "http://35.185.32.97/quote/AAPL";

        System.out.println("Start AAPL data polling system");

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                getData(url);
            }
        };

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);

        try {
            scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        scheduledExecutorService.shutdown();
        System.out.println("Close AAPL data polling system");
    }


    public static String getData(String endPoint) {
        String data = "";
        try {
            URL url = new URL(endPoint);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String response = bufferedReader.readLine();

            JSONObject jsonObject = new JSONObject(response);
            String symbol = jsonObject.getString("symbol");
            if(!symbol.equals("AAPL"))
                return "";

            Double regularMarketPrice = jsonObject.getDouble("regularMarketPrice");
            BigDecimal sharesOutstanding = jsonObject.getBigDecimal("sharesOutstanding");

            Stock stock = new Stock(symbol, regularMarketPrice, sharesOutstanding);

            System.out.println(stock.toString());

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
