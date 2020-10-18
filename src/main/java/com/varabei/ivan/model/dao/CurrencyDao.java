package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.Currency;
import com.varabei.ivan.model.exception.DaoException;

import java.math.BigDecimal;

public interface CurrencyDao {
    BigDecimal findCurrencyCostInUsd(Currency currency) throws DaoException;
}
