package com.varabei.ivan.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class FormatExpressionLanguage {
    private FormatExpressionLanguage() {
    }

    public static void main(String... sdf) {
        System.err.println(new BigDecimal("0.32").longValue());
    }

    public static String formatLocalDate(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatCardNumber(String cardNumber) {
        return String.join(" ", cardNumber.split("(?<=\\G.{4})"));
    }
}