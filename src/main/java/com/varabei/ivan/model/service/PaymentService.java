package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface PaymentService {
//    void makePayment(Long sourceCardId, String sourceCardCvc, String destinationCardNumber,
//                     YearMonth destinationCardValidThru, BigDecimal amount) throws ServiceException;

    boolean makePayment(Map<String, String> paymentData) throws ServiceException;

    int findAmountOfPagesByCardId(Long cardId, int limit) throws ServiceException;

    List<Payment> findPaymentsByCardId(Long cardId, int limit, int offset) throws ServiceException;

    List<Payment> findOutgoingPayments(Long cardId) throws ServiceException;

    List<Payment> findIncomingPayments(Long cardId) throws ServiceException;
}
