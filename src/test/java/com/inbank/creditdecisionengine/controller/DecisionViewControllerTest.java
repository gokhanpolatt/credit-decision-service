package com.inbank.creditdecisionengine.controller;

import com.inbank.creditdecisionengine.BaseIntegrationTest;
import com.inbank.creditdecisionengine.config.CreditDecisionConfig;
import com.inbank.creditdecisionengine.service.CreditDecisionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(DecisionViewController.class)
class DecisionViewControllerTest extends BaseIntegrationTest {

    @MockBean
    private CreditDecisionService creditDecisionService;

    @MockBean
    private CreditDecisionConfig creditDecisionConfig;

    private String creditScoreThreshold = "1";
    private String maxCreditAmount = "10000";
    private String minCreditAmount = "2000";
    private String maxLoanPeriod = "12";
    private String minLoanPeriod = "60";

    @BeforeEach
    public void before() {
        Mockito.when(creditDecisionConfig.getCreditScoreThreshold())
                .thenReturn(creditScoreThreshold);
        Mockito.when(creditDecisionConfig.getMaxCreditAmount())
                .thenReturn(maxCreditAmount);
        Mockito.when(creditDecisionConfig.getMinCreditAmount())
                .thenReturn(minCreditAmount);
        Mockito.when(creditDecisionConfig.getMaxLoanPeriod())
                .thenReturn(maxLoanPeriod);
        Mockito.when(creditDecisionConfig.getMinLoanPeriod())
                .thenReturn(minLoanPeriod);
    }

    @Test
    public void shouldReturnIndexPageWithCorrectAttributes()
            throws Exception {
        mockMvc
                .perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("creditLimitations"));
    }

}
