package com.yoonhee.sample.model;

import java.math.BigDecimal;

public class Stock {
    private String symbol;
    private double regularMarketPrice;
    private BigDecimal sharesOutstanding;
    private BigDecimal marketCapitalization;

    public Stock(String symbol, double regularMarketPrice, BigDecimal sharesOutstanding) {
        this.symbol = symbol;
        this.regularMarketPrice = regularMarketPrice;
        this.sharesOutstanding = sharesOutstanding;
        this.marketCapitalization = sharesOutstanding.multiply(BigDecimal.valueOf(regularMarketPrice));
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", regularMarketPrice=" + regularMarketPrice +
                ", sharesOutstanding=" + sharesOutstanding +
                ", marketCapitalization=" + marketCapitalization +
                '}';
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getRegularMarketPrice() {
        return regularMarketPrice;
    }

    public void setRegularMarketPrice(double regularMarketPrice) {
        this.regularMarketPrice = regularMarketPrice;
    }

    public BigDecimal getSharesOutstanding() {
        return sharesOutstanding;
    }

    public void setSharesOutstanding(BigDecimal sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public BigDecimal getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(BigDecimal marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }
}
