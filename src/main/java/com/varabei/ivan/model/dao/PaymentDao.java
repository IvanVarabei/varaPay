package com.varabei.ivan.model.dao;

import java.math.BigDecimal;

public interface PaymentDao {
    void makePayment(Long sourceCardId, Long destinationCardId, BigDecimal amount) throws DaoException;
}
