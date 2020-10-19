package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.model.dao.CardDao;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.PaymentDao;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.PaymentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentServiceImpl implements PaymentService {
    private static final PaymentDao paymentDao = DaoFactory.getInstance().getPaymentDao();
    CardDao cardDao = DaoFactory.getInstance().getCardDao();
    private static final int AMOUNT_OF_FIGURES_IN_CARD_NUMBER = 16;
    private static final String REGEX_NOT_DIGIT = "\\D+";

    @Override
    public void makePayment(Long sourceCardId, String sourceCardCvc, String destinationCardNumber,
                            YearMonth destinationCardValidThru, BigDecimal amount) throws ServiceException {
        try {
            String clearNumber = destinationCardNumber.replaceAll(REGEX_NOT_DIGIT, "");
            if (clearNumber.length() == AMOUNT_OF_FIGURES_IN_CARD_NUMBER) {
//                cardDao.ifExists(sourceCardId, sourceCardCvc);
//                cardDao.ifExists(clearNumber, destinationCardValidThru);
                paymentDao.makePayment(sourceCardId, clearNumber, amount.movePointRight(2).longValue());
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findAmountOfPagesByCardId(Long cardId, int limit) throws ServiceException {
        try {
            Long numberOfRecords = paymentDao.findNumberOfRecordsByCardId(cardId);
            return (int) Math.ceil(numberOfRecords * 1d / Const.WebPageConfig.RECORDS_PER_PAGE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Payment> findPaymentsByCardId(Long cardId, int limit, int pageIndex) throws ServiceException {
        try {
            return paymentDao.findPaymentsByCardId(cardId, limit, (pageIndex - 1) * limit);
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
