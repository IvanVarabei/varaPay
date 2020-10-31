package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.exception.DaoException;

import java.util.List;

public interface PaymentDao {
    List<Payment> findPaymentsByCardId(Long cardId, int limit, int offset) throws DaoException;

    Long findAmountOfRecordsByCardId(Long cardId) throws DaoException;

    void makePayment(Long sourceCardId, String destinationCardNumber, Long amount) throws DaoException;
}
