package com.inbank.creditdecisionengine.util;

import lombok.experimental.UtilityClass;

import java.text.DecimalFormat;

@UtilityClass
public class DecisionServiceUtil {

    public static final String DEBT_SEGMENT = "debt";
    public static final String SEGMENT_1 = "segment 1";
    public static final String SEGMENT_2 = "segment 2";
    public static final String SEGMENT_3 = "segment 3";

    private final DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public static Double formatToTwoDecimal(Double value) {
        String formattedCreditScore = decimalFormat.format(value);
        return Double.parseDouble(formattedCreditScore);
    }
}
