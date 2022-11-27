package com.inbank.creditdecisionengine.util;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.AssertionErrors;

class DecisionServiceUtilTest {


    @Test
    public void shouldFormatDoubleValues_withTwoDecimal() {
        Double value = 12.345d;

        Double result = DecisionServiceUtil.formatToTwoDecimal(value);
        Double expectedValue = 12.35d;

        AssertionErrors.assertEquals("Double value is formatted with two decimal", expectedValue, result);
    }
}
