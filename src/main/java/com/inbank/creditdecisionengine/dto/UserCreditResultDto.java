package com.inbank.creditdecisionengine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
