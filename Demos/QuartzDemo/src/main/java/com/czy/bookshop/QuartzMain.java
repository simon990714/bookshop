package com.czy.bookshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuartzMain {
    public static void main(String[] args) {
        SpringApplication.run(QuartzMain.class);
    }
}