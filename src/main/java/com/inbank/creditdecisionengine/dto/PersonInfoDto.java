package com.inbank.creditdecisionengine.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoDto implements Serializable {

    private String identityNumber;

    private String segment;

    private Integer creditModifier;
}
