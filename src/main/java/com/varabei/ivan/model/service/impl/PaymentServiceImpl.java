package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.PaymentDao;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.PaymentService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentServiceImpl implements PaymentService {
    PaymentDao paymentDao = DaoFactory.getInstance().getPaymentDao();

    @Override
    public void makePayment(Long sourceCardId, String destinationCardNumber, BigDecimal amount) throws ServiceException {
        try {
            String properNumber = destinationCardNumber.replaceAll("\\D+","");
            if(properNumber.length() == 16) {
                paymentDao.makePayment(sourceCardId, properNumber, amount.movePointRight(2).longValue());
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long findNumberOfRecordsByCardId(Long cardId) throws ServiceException{
        try {
            return paymentDao.findNumberOfRecordsByCardId(cardId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Payment> findPaymentsByCardId(Long cardId, int limit, int offset) throws ServiceException {
        try {
            return paymentDao.findPaymentsByCardId(cardId, limit, offset);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Payment> findOutgoingPayments(Long cardId) throws ServiceException {
        try {
            return paymentDao.findOutgoingPayments(cardId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Payment> findIncomingPayments(Long cardId) throws ServiceException {
        try {
            return paymentDao.findIncomingPayments(cardId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
