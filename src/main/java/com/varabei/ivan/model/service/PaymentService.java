package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    List<Payment> findPaymentsByCardId(Long cardId, int limit, int offset) throws ServiceException;

    int findAmountOfPagesByCardId(Long cardId, int limit) throws ServiceException;

    boolean makePayment(Map<String, String> paymentData) throws ServiceException;
}
