package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    List<Payment> findAll() throws ServiceException;
    void makePayment(Long sourceCardId, Long destinationCardId, BigDecimal amount) throws ServiceException;
}
