package com.inbank.creditdecisionengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CreditDecisionEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditDecisionEngineApplication.class, args);
    }

}
