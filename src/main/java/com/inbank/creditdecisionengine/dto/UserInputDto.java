package com.inbank.creditdecisionengine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDto {

    private String identityNumber;

    private Integer requestedLoanPeriod;

    private Double requestedLoanAmount;
}
