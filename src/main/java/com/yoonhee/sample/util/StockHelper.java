package com.yoonhee.sample.util;

import com.yoonhee.sample.model.Stock;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static com.yoonhee.sample.App.maxMarketCapDiff;

public class StockHelper {
    public boolean isAAPLQuote(String symbol){
        return symbol.equals("AAPL");
    }

    public Stock parseJson(JSONObject jsonObject){
        String symbol = jsonObject.getString("symbol");
        Double regularMarketPrice = jsonObject.getDouble("regularMarketPrice");
        BigDecimal sharesOutstanding = jsonObject.getBigDecimal("sharesOutstanding");

        return new Stock(symbol, regularMarketPrice, sharesOutstanding);
    }

    public void printStatus(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Maximum Change in Market Capitalization: " + maxMarketCapDiff);
        System.out.println(timestamp);

        System.out.println("===============================");
    }
}
