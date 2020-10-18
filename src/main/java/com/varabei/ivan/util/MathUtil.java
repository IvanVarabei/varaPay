package com.varabei.ivan.util;

import java.util.Random;

public class MathUtil {
    private static final int DECIMAL_RADIX = 10;
    private static final Random random = new Random();

    private MathUtil() {
    }

    public static String generateRandom(int length) {
        int min = (int) Math.pow(DECIMAL_RADIX, length - 1d);
        int max = (int) Math.pow(DECIMAL_RADIX, length);
        return Integer.toString(random.nextInt(max - min) + min);
    }
}
