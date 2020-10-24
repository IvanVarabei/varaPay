package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Currency;
import com.varabei.ivan.model.exception.ServiceException;

import java.math.BigDecimal;

public interface CurrencyService {
    BigDecimal convertUsdToAnotherCurrency(BigDecimal usdAmount, Currency currency);
}
