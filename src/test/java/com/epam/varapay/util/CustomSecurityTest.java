package com.epam.varapay.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CustomSecurityTest {
    @Test
    public void generateRandom() {
        int expectedLength = 64;
        int actualLength = CustomSecurity.generateRandom(expectedLength).length();

        assertEquals(actualLength, expectedLength);
    }

    @Test
    public void generateHash() {
        int expectedLength = 64;
        int actualLength = CustomSecurity.generateHash("hello").length();

        assertEquals(actualLength, expectedLength);
    }
}
