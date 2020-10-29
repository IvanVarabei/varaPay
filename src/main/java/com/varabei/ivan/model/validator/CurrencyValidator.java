package com.varabei.ivan.model.validator;

import com.varabei.ivan.model.service.ErrorInfo;
import com.varabei.ivan.model.entity.Currency;
import com.varabei.ivan.model.service.DataTransferMapKey;

import java.util.Arrays;
import java.util.Map;

public class CurrencyValidator {
    private static final String DIGITS = "^\\d+\\.?\\d*$";

    public boolean isValidDataToConvert(Map<String, String> dataToConvert) {
        boolean isValid = isValidAmount(dataToConvert);
        isValid &= isValidCurrency(dataToConvert);
        return isValid;
    }

    private boolean isValidAmount(Map<String, String> dataToConvert) {
        String amount = dataToConvert.get(DataTransferMapKey.AMOUNT);
        if (!amount.matches(DIGITS)) {
            dataToConvert.put(DataTransferMapKey.AMOUNT, ErrorInfo.NOT_NUMBER.name().toLowerCase());
            return false;
        }
        return true;
    }

    private boolean isValidCurrency(Map<String, String> dataToConvert) {
        String currency = dataToConvert.get(DataTransferMapKey.CURRENCY);
        if (Arrays.stream(Currency.values()).noneMatch(c -> currency.equalsIgnoreCase(c.name()))) {
            dataToConvert.put(DataTransferMapKey.CURRENCY, ErrorInfo.CAN_NOT_BE_EMPTY.name().toLowerCase());
            return false;
        }
        return true;
    }
}
