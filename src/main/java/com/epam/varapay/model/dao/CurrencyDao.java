package com.epam.varapay.model.dao;

import com.epam.varapay.model.entity.CustomCurrency;
import com.epam.varapay.model.exception.DaoException;

import java.math.BigDecimal;

public interface CurrencyDao {
    BigDecimal findCurrencyCostInUsd(CustomCurrency currency) throws DaoException;
}
