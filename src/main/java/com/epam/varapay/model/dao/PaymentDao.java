package com.epam.varapay.model.dao;

import com.epam.varapay.model.entity.Payment;
import com.epam.varapay.model.exception.DaoException;

import java.util.List;

public interface PaymentDao {
    List<Payment> findPaymentsByCardId(Long cardId, int limit, int offset) throws DaoException;

    Long findAmountOfRecordsByCardId(Long cardId) throws DaoException;

    void makePayment(Long sourceCardId, String destinationCardNumber, Long amount) throws DaoException;
}
