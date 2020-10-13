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
    @Override
    public List<Payment> findAll() throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        try {
            return userDao.findAll().stream()
                    .flatMap(u -> u.getAccounts().stream()
                            .flatMap(a -> a.getCards().stream()
                                    .flatMap(c -> c.getPayments().stream())))
                    .distinct().collect(Collectors.toList());
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void makePayment(Long sourceCardId, String destinationCardNumber, BigDecimal amount) throws ServiceException {
        PaymentDao paymentDao = DaoFactory.getInstance().getPaymentDao();
        try {
            paymentDao.makePayment(sourceCardId, destinationCardNumber, amount);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }
}
