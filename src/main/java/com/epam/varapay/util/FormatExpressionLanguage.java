package com.epam.varapay.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormatExpressionLanguage {
    private static final String REGEX_TO_SPLIT_CARD_NUMBER = "(?<=\\G.{4})";

    private FormatExpressionLanguage() {
    }

    public static String formatLocalDate(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatCardNumber(String cardNumber) {
        return String.join(" ", cardNumber.split(REGEX_TO_SPLIT_CARD_NUMBER));
    }
}