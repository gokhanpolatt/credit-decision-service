package com.inbank.creditdecisionengine.service;

import com.inbank.creditdecisionengine.BaseMockitoTest;
import com.inbank.creditdecisionengine.config.CreditDecisionConfig;
import com.inbank.creditdecisionengine.dto.PersonInfoDto;
import com.inbank.creditdecisionengine.dto.UserCreditResultDto;
import com.inbank.creditdecisionengine.dto.UserInputDto;
import com.inbank.creditdecisionengine.util.DecisionServiceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

class CreditDecisionServiceTest extends BaseMockitoTest {

    @InjectMocks
    private CreditDecisionService creditDecisionService;

    @Mock
    private CreditDecisionConfig creditDecisionConfig;

    @Mock
    private ThirdPartyCommunicationService thirdPartyCommunicationService;

    private String creditScoreThreshold = "1";
    private String maxCreditAmount = "10000";
    private String minCreditAmount = "2000";
    private String maxLoanPeriod = "60";
    private String minLoanPeriod = "12";

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
    public void shouldReturnFalse_IfPersonHasDebt() {
        String identityNumber = "any";
        Mockito.when(thirdPartyCommunicationService.getPersonInfo(identityNumber))
                .thenReturn(PersonInfoDto.builder().segment(DecisionServiceUtil.DEBT_SEGMENT).build());
        UserCreditResultDto creditResult = creditDecisionService.getCreditResult(UserInputDto.builder().identityNumber(identityNumber).build());

        assertFalse("Person is not eligible to get a loan", creditResult.isCreditSuitable());
    }

    @Test
    public void shouldReturnTrue_IfPersonCreditScore_isHigherThanTreshold() {
        String identityNumber = "any";
        Mockito.when(thirdPartyCommunicationService.getPersonInfo(identityNumber))
                .thenReturn(PersonInfoDto.builder().creditModifier(500).build());
        UserCreditResultDto creditResult = creditDecisionService.getCreditResult(UserInputDto.builder().
                requestedLoanAmount(Double.parseDouble(minCreditAmount)).requestedLoanPeriod(25).identityNumber(identityNumber).build());

        assertTrue("Person is eligible to get a loan", creditResult.isCreditSuitable());
    }

    @Test
    public void shouldReturnHigherLoanAmount_IfPersonIsEligible() {
        String identityNumber = "any";
        Double requestedLoanAmount = 2000d;
        Mockito.when(thirdPartyCommunicationService.getPersonInfo(identityNumber))
                .thenReturn(PersonInfoDto.builder().creditModifier(200).build());
        UserCreditResultDto creditResult = creditDecisionService.getCreditResult(UserInputDto.builder().
                requestedLoanAmount(requestedLoanAmount).requestedLoanPeriod(25).identityNumber(identityNumber).build());

        assertTrue("Person is eligible to get higher loan", requestedLoanAmount < creditResult.getLoanAmount());
    }

    @Test
    public void shouldReturnLowerLoanAmount_IfPersonIsNotEligibleToGetRequestedLoan() {
        String identityNumber = "any";
        Double requestedLoanAmount = 8000d;
        Mockito.when(thirdPartyCommunicationService.getPersonInfo(identityNumber))
                .thenReturn(PersonInfoDto.builder().creditModifier(100).build());
        UserCreditResultDto creditResult = creditDecisionService.getCreditResult(UserInputDto.builder().
                requestedLoanAmount(requestedLoanAmount).requestedLoanPeriod(25).identityNumber(identityNumber).build());

        assertTrue("Person is eligible to get lower loan", requestedLoanAmount > creditResult.getLoanAmount());
    }

    @Test
    public void shouldReturnHigherLoanPeriod_IfPersonIsNotEligibleToGetInRequestedPeriod() {
        String identityNumber = "any";
        Integer requestedLoanPeriod = 25;
        Mockito.when(thirdPartyCommunicationService.getPersonInfo(identityNumber))
                .thenReturn(PersonInfoDto.builder().creditModifier(50).build());
        UserCreditResultDto creditResult = creditDecisionService.getCreditResult(UserInputDto.builder().
                requestedLoanAmount(3000d).requestedLoanPeriod(requestedLoanPeriod).identityNumber(identityNumber).build());

        assertTrue("Person is eligible to get the loan in longer period", requestedLoanPeriod < creditResult.getPeriod());
    }

    @Test
    public void shouldReturnNewLoanAmountWithNewLoanPeriod_IfPersonIsNotEligibleToGetRequestedLoanInRequestedPeriod() {
        String identityNumber = "any";
        Integer requestedLoanPeriod = 25;
        Double requestedLoanAmount = 3000d;
        Mockito.when(thirdPartyCommunicationService.getPersonInfo(identityNumber))
                .thenReturn(PersonInfoDto.builder().creditModifier(40).build());
        UserCreditResultDto creditResult = creditDecisionService.getCreditResult(UserInputDto.builder().
                requestedLoanAmount(requestedLoanAmount).requestedLoanPeriod(requestedLoanPeriod).identityNumber(identityNumber).build());

        assertTrue("Person is eligible to get lower loan in longer period", requestedLoanPeriod < creditResult.getPeriod());
        assertTrue("Person is eligible to get lower loan in longer period", requestedLoanAmount > creditResult.getLoanAmount());
    }

    @Test
    public void shouldReturnFalse_IfPersonCreditScoreIsNotEligibleToGetMinLoanInMaxPeriod() {
        String identityNumber = "any";
        Mockito.when(thirdPartyCommunicationService.getPersonInfo(identityNumber))
                .thenReturn(PersonInfoDto.builder().creditModifier(30).build());
        UserCreditResultDto creditResult = creditDecisionService.getCreditResult(UserInputDto.builder().
                requestedLoanAmount(5000d).requestedLoanPeriod(30).identityNumber(identityNumber).build());

        assertFalse("Person is not eligible to get any loan", creditResult.isCreditSuitable());
    }
}
