package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    void makePayment(Long sourceCardId, String destinationCardLong, BigDecimal amount) throws ServiceException;

    Long findNumberOfRecordsByCardId(Long cardId) throws ServiceException;

    List<Payment> findPaymentsByCardId(Long cardId, int limit, int offset) throws ServiceException;

    List<Payment> findOutgoingPayments(Long cardId) throws ServiceException;

    List<Payment> findIncomingPayments(Long cardId) throws ServiceException;
}
