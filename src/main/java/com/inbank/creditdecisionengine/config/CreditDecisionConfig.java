package com.inbank.creditdecisionengine.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreditDecisionConfig {

    @Value("${inbank.decisionService.creditScoreThreshold}")
    private String creditScoreThreshold;

    @Value("${inbank.decisionService.minCreditAmount}")
    private String minCreditAmount;

    @Value("${inbank.decisionService.maxCreditAmount}")
    private String maxCreditAmount;

    @Value("${inbank.decisionService.minLoanPeriod}")
    private String minLoanPeriod;

    @Value("${inbank.decisionService.maxLoanPeriod}")
    private String maxLoanPeriod;

    public String getCreditScoreThreshold() {
        return creditScoreThreshold;
    }

    public void setCreditScoreThreshold(String creditScoreThreshold) {
        this.creditScoreThreshold = creditScoreThreshold;
    }

    public String getMinCreditAmount() {
        return minCreditAmount;
    }

    public void setMinCreditAmount(String minCreditAmount) {
        this.minCreditAmount = minCreditAmount;
    }

    public String getMaxCreditAmount() {
        return maxCreditAmount;
    }

    public void setMaxCreditAmount(String maxCreditAmount) {
        this.maxCreditAmount = maxCreditAmount;
    }

    public String getMinLoanPeriod() {
        return minLoanPeriod;
    }

    public void setMinLoanPeriod(String minLoanPeriod) {
        this.minLoanPeriod = minLoanPeriod;
    }

    public String getMaxLoanPeriod() {
        return maxLoanPeriod;
    }

    public void setMaxLoanPeriod(String maxLoanPeriod) {
        this.maxLoanPeriod = maxLoanPeriod;
    }
}
