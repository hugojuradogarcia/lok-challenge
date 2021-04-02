package com.lok.functions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SumTest {
    @Test
    @DisplayName("the sum method should be correct")
    public void TestReverseStringSuccessful() {
        Sum sum = new Sum();

        double result = sum.calculateSum(5);

        assertTrue(15 == result);
    }
}
