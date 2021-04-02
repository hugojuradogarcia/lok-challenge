package com.lok.functions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PowTest {
    @Test
    @DisplayName("The pow method should be correct")
    public void TestReverseStringSuccessful() {
        Pow pow = new Pow();

        int result = pow.calculatePow(5);

        assertTrue(55 == result);
    }
}
