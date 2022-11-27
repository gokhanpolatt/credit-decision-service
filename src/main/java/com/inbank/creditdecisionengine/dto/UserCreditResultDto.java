package com.inbank.creditdecisionengine.dto;

import lombok.Data;

@Data
public class UserCreditResultDto {

    private PersonInfoDto personInfo;

    private UserInputDto userInput;

    private Double creditScore;

    private Double creditScoreThreshold;

    private Double loanAmount;

    private boolean isCreditSuitable;

    private boolean isSpecialOfferSuitable;

    private int period;
}
