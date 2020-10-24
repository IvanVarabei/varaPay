package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.entity.Currency;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.CurrencyService;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyServiceImpl implements CurrencyService {
    private static final int AMOUNT_OF_DIGITS_AFTER_COLUMN = 8;

    @Override
    public BigDecimal convertUsdToAnotherCurrency(BigDecimal usdAmount, Currency currency) {
        return usdAmount.divide(currency.getCost(), AMOUNT_OF_DIGITS_AFTER_COLUMN, RoundingMode.CEILING);
    }
}
