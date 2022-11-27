package com.inbank.creditdecisionengine.controller;

import com.inbank.creditdecisionengine.BaseIntegrationTest;
import com.inbank.creditdecisionengine.config.CreditDecisionConfig;
import com.inbank.creditdecisionengine.dto.UserCreditResultDto;
import com.inbank.creditdecisionengine.dto.UserInputDto;
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

    private final String creditScoreThreshold = "1";
    private final String maxCreditAmount = "10000";
    private final String minCreditAmount = "2000";
    private final String maxLoanPeriod = "12";
    private final String minLoanPeriod = "60";

    private final String identityNumber = "123";
    private final Integer requestedLoanPeriod = 12;
    private final Double requestedLoanAmount = 5000d;

    @BeforeEach
    public void before() {
        UserInputDto userInputDto = UserInputDto.builder()
                .requestedLoanPeriod(requestedLoanPeriod)
                .requestedLoanAmount(requestedLoanAmount)
                .identityNumber(identityNumber)
                .build();

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
        Mockito.when(creditDecisionService.getCreditResult(userInputDto))
                .thenReturn(UserCreditResultDto.builder().build());
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

    @Test
    public void shouldReturnResultFragmentWithCorrectAttributes()
            throws Exception {
        mockMvc
                .perform(get("/result")
                        .param("id", identityNumber)
                        .param("amount", String.valueOf(requestedLoanAmount))
                        .param("period", String.valueOf(requestedLoanPeriod)))
                .andExpect(status().isOk())
                .andExpect(view().name("result_fragment :: result_frag"))
                .andExpect(model().attributeExists("result"));
    }
}
