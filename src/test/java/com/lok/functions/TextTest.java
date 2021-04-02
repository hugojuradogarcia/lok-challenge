package com.lok.functions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTest {
    @Test
    @DisplayName("I should reverse the string correctly")
    public void TestReverseStringSuccessful() {
        Text text = new Text();

        String result = text.reverseString("Karen");

        assertEquals("neraK", result);
    }
}
