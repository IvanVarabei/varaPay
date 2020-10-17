package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentDao {
    Long findNumberOfRecordsByCardId(Long cardId) throws DaoException;

    void makePayment(Long sourceCardId, String destinationCardNumber, Long amount) throws DaoException;

    List<Payment> findPaymentsByCardId(Long cardId, int limit, int offset) throws DaoException;

    List<Payment> findOutgoingPayments(Long cardId) throws DaoException;

    List<Payment> findIncomingPayments(Long cardId) throws DaoException;
}
