package com.varabei.ivan.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormatExpressionLanguage {
    private FormatExpressionLanguage() {
    }

    public static String formatLocalDate(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatCardNumber(String cardNumber) {
        return String.join(" ", cardNumber.split("(?<=\\G.{4})"));
    }
}