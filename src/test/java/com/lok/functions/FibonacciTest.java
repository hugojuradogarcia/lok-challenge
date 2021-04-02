package com.lok.functions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FibonacciTest {
    @Test
    @DisplayName("The fibonacci method should be correct")
    public void TestReverseStringSuccessful() {
        Fibonacci fibonacci = new Fibonacci();

        List<Integer> serie = fibonacci.getFibonacciSeries(10);

        assertEquals(0, serie.get(0).intValue());
        assertEquals(34, serie.get(serie.size() - 1).intValue());
        assertEquals(10, serie.size());
    }
}
