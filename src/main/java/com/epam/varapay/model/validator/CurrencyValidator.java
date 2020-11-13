package com.epam.varapay.model.validator;

import com.epam.varapay.model.entity.CustomCurrency;
import com.epam.varapay.model.service.DataTransferMapKey;
import com.epam.varapay.model.service.ErrorMessage;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

public class CurrencyValidator {
    private static final Pattern AMOUNT_PATTERN =
            Pattern.compile("^([1-9]\\d{1,8}(\\.\\d{0,2})?)|(0\\.((\\d[1-9])|([1-9]\\d)))$");

    private CurrencyValidator() {
    }

    private static class CurrencyValidatorHolder {
        private static final CurrencyValidator CURRENCY_VALIDATOR_INSTANCE = new CurrencyValidator();
    }

    public static CurrencyValidator getInstance() {
        return CurrencyValidatorHolder.CURRENCY_VALIDATOR_INSTANCE;
    }

    public boolean isValidDataToConvert(Map<String, String> dataToConvert) {
        boolean isValid = checkAmount(dataToConvert);
        isValid &= checkCurrency(dataToConvert);
        return isValid;
    }

    private boolean checkAmount(Map<String, String> paymentData) {
        String amount = paymentData.get(DataTransferMapKey.AMOUNT);
        if (amount == null || !AMOUNT_PATTERN.matcher(amount).matches()) {
            paymentData.put(DataTransferMapKey.AMOUNT, ErrorMessage.AMOUNT.toString());
            return false;
        }
        return true;
    }

    private boolean checkCurrency(Map<String, String> dataToConvert) {
        String currency = dataToConvert.get(DataTransferMapKey.CURRENCY);
        if (currency == null || Arrays.stream(CustomCurrency.values())
                .noneMatch(c -> currency.equalsIgnoreCase(c.name()))) {
            dataToConvert.put(DataTransferMapKey.CURRENCY, ErrorMessage.CAN_NOT_BE_EMPTY.toString());
            return false;
        }
        return true;
    }
}
