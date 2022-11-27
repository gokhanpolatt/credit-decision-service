package com.inbank.creditdecisionengine.service;

import com.inbank.creditdecisionengine.config.CreditDecisionConfig;
import com.inbank.creditdecisionengine.dto.PersonInfoDto;
import com.inbank.creditdecisionengine.dto.UserCreditResultDto;
import com.inbank.creditdecisionengine.dto.UserInputDto;
import com.inbank.creditdecisionengine.util.DecisionServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CreditDecisionService {

    private final ThirdPartyCommunicationService thirdPartyCommunicationService;

    private final CreditDecisionConfig creditDecisionConfig;

    public CreditDecisionService(ThirdPartyCommunicationService thirdPartyCommunicationService,
                                 CreditDecisionConfig creditDecisionConfig) {
        this.thirdPartyCommunicationService = thirdPartyCommunicationService;
        this.creditDecisionConfig = creditDecisionConfig;
    }

    public UserCreditResultDto getCreditResult(UserInputDto userInputDto) {
        PersonInfoDto personInfoDto = getPersonInfoFromExternalService(userInputDto.getIdentityNumber());
        if (personInfoDto == null) {
            log.info("User not found");
            return null;
        }
        log.info("Person info is gotten successfully");

        UserCreditResultDto userCreditResultDto = convertUserDataToCreditResult(userInputDto, personInfoDto);
        userCreditResultDto.setPersonInfo(personInfoDto);

        return userCreditResultDto;
    }

    public PersonInfoDto getPersonInfoFromExternalService(String identityNumber) {
        return thirdPartyCommunicationService.getPersonInfo(identityNumber);
    }

    public UserCreditResultDto convertUserDataToCreditResult(UserInputDto userInputDto, PersonInfoDto personInfoDto) {
        UserCreditResultDto userCreditResultDto = UserCreditResultDto.builder().
                personInfo(personInfoDto)
                .userInput(userInputDto)
                .build();

        if (DecisionServiceUtil.DEBT_SEGMENT.equals(personInfoDto.getSegment())) {
            log.info("User is not eligible to get loan");
            userCreditResultDto.setCreditSuitable(false);
        } else {
            Double creditScore = calculateCreditScore(personInfoDto.getCreditModifier(),
                    userInputDto.getRequestedLoanAmount(), userInputDto.getRequestedLoanPeriod());
            userCreditResultDto.setCreditScore(creditScore);
            userCreditResultDto.setCreditScoreThreshold(Double.parseDouble(creditDecisionConfig.getCreditScoreThreshold()));
            if (creditScore >= 1) {
                log.info("User's credit score is eligible to get the selected loan {} within the selected period {}",
                        userInputDto.getRequestedLoanAmount(), userInputDto.getRequestedLoanPeriod());
                log.info("Calculating max loan that can be given..");
                userCreditResultDto.setCreditSuitable(true);
                Double maxCreditAmountWithSelectedLoanPeriod = calculateMaxCreditAmountWithSelectedLoanPeriod(
                        personInfoDto.getCreditModifier(), userInputDto.getRequestedLoanPeriod());
                if (Double.parseDouble(creditDecisionConfig.getMaxCreditAmount()) >= maxCreditAmountWithSelectedLoanPeriod) {
                    userCreditResultDto.setLoanAmount(maxCreditAmountWithSelectedLoanPeriod);
                } else {
                    userCreditResultDto.setLoanAmount(Double.parseDouble(creditDecisionConfig.getMaxCreditAmount()));
                }
            } else {
                log.info("User's credit score is NOT eligible to get the selected loan {} within the selected period {}",
                        userInputDto.getRequestedLoanAmount(), userInputDto.getRequestedLoanPeriod());
                log.info("Calculating other options..");
                Double maxCreditAmountWithSelectedLoanPeriod = calculateMaxCreditAmountWithSelectedLoanPeriod(
                        personInfoDto.getCreditModifier(), userInputDto.getRequestedLoanPeriod());
                Integer minSuitablePeriodWithSelectedLoanAmount = calculateMinSuitablePeriodWithSelectedLoanAmount(
                        personInfoDto.getCreditModifier(), userInputDto.getRequestedLoanAmount());
                if (Double.parseDouble(creditDecisionConfig.getMinCreditAmount()) <= maxCreditAmountWithSelectedLoanPeriod) {
                    userCreditResultDto.setCreditSuitable(true);
                    userCreditResultDto.setLoanAmount(maxCreditAmountWithSelectedLoanPeriod);
                } else if (Integer.parseInt(creditDecisionConfig.getMaxLoanPeriod()) >= minSuitablePeriodWithSelectedLoanAmount
                        && Integer.parseInt(creditDecisionConfig.getMinLoanPeriod()) <= minSuitablePeriodWithSelectedLoanAmount) {
                    userCreditResultDto.setCreditSuitable(true);
                    userCreditResultDto.setPeriod(minSuitablePeriodWithSelectedLoanAmount);
                } else {
                    Double loanAmount = calculateHowMuchUserCanGetForMaxPeriod(personInfoDto.getCreditModifier());
                    if (loanAmount >= Double.parseDouble(creditDecisionConfig.getMinCreditAmount())) {
                        userCreditResultDto.setCreditSuitable(true);
                        userCreditResultDto.setSpecialOfferSuitable(true);
                        userCreditResultDto.setLoanAmount(loanAmount);
                        userCreditResultDto.setPeriod(Integer.parseInt(creditDecisionConfig.getMaxLoanPeriod()));
                    } else {
                        userCreditResultDto.setCreditSuitable(false);
                    }
                }
            }
        }

        log.info(String.valueOf(userCreditResultDto));

        return userCreditResultDto;
    }

    public Double calculateCreditScore(Integer creditModifier, Double loanAmount, Integer loanPeriod) {
        Double creditScore = (creditModifier / loanAmount) * loanPeriod;
        return DecisionServiceUtil.formatToTwoDecimal(creditScore);
    }

    public Double calculateMaxCreditAmountWithSelectedLoanPeriod(Integer creditModifier, Integer loanPeriod) {
        Double maxCreditAmount = (creditModifier * loanPeriod)
                / Double.parseDouble(creditDecisionConfig.getCreditScoreThreshold());
        return DecisionServiceUtil.formatToTwoDecimal(maxCreditAmount);
    }

    public Integer calculateMinSuitablePeriodWithSelectedLoanAmount(Integer creditModifier, Double loanAmount) {
        double minSuitablePeriod = (Double.parseDouble(creditDecisionConfig.getCreditScoreThreshold()) * loanAmount)
                / creditModifier;
        return (int) Math.ceil(minSuitablePeriod);
    }

    public Double calculateHowMuchUserCanGetForMaxPeriod(Integer creditModifier) {
        double loanAmount = (creditModifier * Double.parseDouble(creditDecisionConfig.getMaxLoanPeriod()))
                / Double.parseDouble(creditDecisionConfig.getCreditScoreThreshold());
        return DecisionServiceUtil.formatToTwoDecimal(loanAmount);
    }
}
