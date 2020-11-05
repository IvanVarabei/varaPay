package com.epam.varapay.model.service;

import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.entity.Payment;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    List<Payment> findPaymentsByCardId(Long cardId, int limit, int offset) throws ServiceException;

    int findAmountOfPagesByCardId(Long cardId, int limit) throws ServiceException;

    boolean makePayment(Map<String, String> paymentData) throws ServiceException;
}
