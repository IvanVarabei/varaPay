package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.entity.Currency;
import com.varabei.ivan.model.service.CurrencyService;
import com.varabei.ivan.model.service.DataTransferMapKey;
import com.varabei.ivan.model.validator.CurrencyValidator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

public class CurrencyServiceImpl implements CurrencyService {
    private static CurrencyValidator currencyValidator = new CurrencyValidator();
    private static final int AMOUNT_OF_DIGITS_AFTER_COLUMN = 8;

    @Override
    public Optional<BigDecimal> convertUsdToAnotherCurrency(Map<String, String> dataToConvert) {
        if (currencyValidator.isValidDataToConvert(dataToConvert)) {
            BigDecimal amountUsd = new BigDecimal(dataToConvert.get(DataTransferMapKey.AMOUNT));
            BigDecimal currencyCost = Currency.valueOf(dataToConvert.get(DataTransferMapKey.CURRENCY)).getCost();
            BigDecimal amountInChosenCurrency =
                    amountUsd.divide(currencyCost, AMOUNT_OF_DIGITS_AFTER_COLUMN, RoundingMode.CEILING);
            return Optional.of(amountInChosenCurrency);
        }
        return Optional.empty();
    }
}
