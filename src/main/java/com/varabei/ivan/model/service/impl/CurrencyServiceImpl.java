package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.entity.Currency;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.CurrencyService;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyServiceImpl implements CurrencyService {
    private static final int AMOUNT_OF_DIGITS_AFTER_COLUMN = 8;

    @Override
    public BigDecimal convertUsdToAnotherCurrency(BigDecimal usdAmount, Currency currency) throws ServiceException {
        try {
            BigDecimal currencyCost = DaoFactory.getInstance().getCurrencyDao().findCurrencyCostInUsd(currency);
            return usdAmount.divide(currencyCost, AMOUNT_OF_DIGITS_AFTER_COLUMN, RoundingMode.CEILING);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
