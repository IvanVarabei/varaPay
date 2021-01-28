package com.epam.varapay.model.dao.impl;

import com.epam.varapay.exception.DaoException;
import com.epam.varapay.model.dao.CurrencyDao;
import com.epam.varapay.model.entity.CustomCurrency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyDaoImpl implements CurrencyDao {
    private static final String API_EXCHANGE_SOME_CURRENCY_TO_USD =
            "https://min-api.cryptocompare.com/data/price?fsym=%s&tsyms=USD";
    private static final Pattern PRICE_FROM_API_RESPONSE = Pattern.compile("\\d+\\.?\\d");

    @Override
    public BigDecimal findCurrencyCostInUsd(CustomCurrency currency) throws DaoException {
        try {
            URL queryUrl = new URL(String.format(API_EXCHANGE_SOME_CURRENCY_TO_USD, currency.getConciseName()));
            HttpURLConnection connection = (HttpURLConnection) queryUrl.openConnection();
            connection.setDoOutput(true);
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            Matcher matcher = PRICE_FROM_API_RESPONSE.matcher(sb);
            if (matcher.find()) {
                return new BigDecimal(matcher.group());
            } else {
                throw new DaoException("Currency api does`t work");
            }
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }
}
