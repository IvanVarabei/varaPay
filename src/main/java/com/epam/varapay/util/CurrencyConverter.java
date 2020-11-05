package com.epam.varapay.util;

import com.epam.varapay.model.entity.CustomCurrency;
import com.epam.varapay.model.service.DataTransferMapKey;
import com.epam.varapay.model.validator.CurrencyValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

public class CurrencyConverter {
    private static CurrencyConverter instance = new CurrencyConverter();
    private static CurrencyValidator currencyValidator = new CurrencyValidator();
    private static final int AMOUNT_OF_DIGITS_AFTER_COLUMN = 8;

    private CurrencyConverter() {
    }

    public static CurrencyConverter getInstance() {
        return instance;
    }

    public Optional<BigDecimal> convertUsdToAnotherCurrency(Map<String, String> dataToConvert) {
        if (currencyValidator.isValidDataToConvert(dataToConvert)) {
            BigDecimal amountUsd = new BigDecimal(dataToConvert.get(DataTransferMapKey.AMOUNT));
            BigDecimal currencyCost = CustomCurrency.valueOf(dataToConvert.get(DataTransferMapKey.CURRENCY)).getCost();
            BigDecimal amountInChosenCurrency =
                    amountUsd.divide(currencyCost, AMOUNT_OF_DIGITS_AFTER_COLUMN, RoundingMode.CEILING);
            return Optional.of(amountInChosenCurrency);
        }
        return Optional.empty();
    }
}
