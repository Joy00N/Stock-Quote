package com.yoonhee.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Write a java program which polls a simple stock quote API at 5 second intervals and which calculates the following for the stock AAPL
 * 1. Sysout change in market capitalization after 5 minutes upon exit the program
 * 2. Sysout the largest change in market capitalization in a 5 second interval
 */

@SpringBootApplication
@EnableScheduling
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
