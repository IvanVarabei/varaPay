package com.varabei.ivan.model.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public interface CurrencyService {
    Optional<BigDecimal> convertUsdToAnotherCurrency(Map<String, String> dataToConvert);
}
