package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.dao.CardDao;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.PaymentDao;
import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.DataTransferMapKey;
import com.varabei.ivan.model.service.ErrorInfo;
import com.varabei.ivan.model.service.PaymentService;
import com.varabei.ivan.model.validator.PaymentValidator;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {
    private static PaymentValidator paymentValidator = new PaymentValidator();
    private static PaymentDao paymentDao = DaoFactory.getInstance().getPaymentDao();
    private static CardDao cardDao = DaoFactory.getInstance().getCardDao();

    @Override
    public List<Payment> findPaymentsByCardId(Long cardId, int limit, int pageIndex) throws ServiceException {
        try {
            return paymentDao.findPaymentsByCardId(cardId, limit, (pageIndex - 1) * limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int findAmountOfPagesByCardId(Long cardId, int limit) throws ServiceException {
        try {
            Long numberOfRecords = paymentDao.findAmountOfRecordsByCardId(cardId);
            return (int) Math.ceil(numberOfRecords * 1d / limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean makePayment(Map<String, String> paymentData) throws ServiceException {
        Map<String, String> initialMap = new HashMap<>(paymentData);
        if (paymentValidator.isValidPayment(paymentData)) {
            try {
                Long sourceCardId = Long.parseLong(paymentData.get(DataTransferMapKey.CARD_ID));
                String destinationCardNumber = paymentData.get(DataTransferMapKey.NUMBER).replace(" ", "");
                BigDecimal amount = new BigDecimal(paymentData.get(DataTransferMapKey.AMOUNT));
                checkSourceCard(paymentData, amount);
                checkDestinationCard(paymentData);
                if (paymentData.equals(initialMap)) {
                    paymentDao.makePayment(sourceCardId, destinationCardNumber, amount.movePointRight(2).longValue());
                    return true;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return false;
    }

    private void checkSourceCard(Map<String, String> paymentData, BigDecimal amount) throws DaoException {
        Long sourceCardId = Long.parseLong(paymentData.get(DataTransferMapKey.CARD_ID));
        Card sourceCard = cardDao.findById(sourceCardId).get();
        if (!sourceCard.getCvc().equals(paymentData.get(DataTransferMapKey.CVC))) {
            paymentData.put(DataTransferMapKey.CVC, ErrorInfo.CVC.toString());
        }
        if (sourceCard.getAccount().getBalance().compareTo(amount) < 0) {
            paymentData.put(DataTransferMapKey.AMOUNT, ErrorInfo.NOT_ENOUGH_BALANCE.toString());
        }
        if (!sourceCard.getAccount().isActive()) {
            paymentData.put(DataTransferMapKey.CARD_ID, ErrorInfo.SOURCE_ACCOUNT_BLOCKED.toString());
        }
    }

    private void checkDestinationCard(Map<String, String> paymentData) throws DaoException {
        String destinationCardNumber = paymentData.get(DataTransferMapKey.NUMBER).replace(" ", "");
        YearMonth validThru = YearMonth.parse(paymentData.get(DataTransferMapKey.VALID_THRU));
        Optional<Card> destinationCard = cardDao.findByCardNumberAndValidThru(destinationCardNumber, validThru);
        if (destinationCard.isPresent()) {
            if (!destinationCard.get().getAccount().isActive()) {
                paymentData.put(DataTransferMapKey.NUMBER, ErrorInfo.DESTINATION_ACCOUNT_BLOCKED.toString());
            }
        } else {
            paymentData.put(DataTransferMapKey.NUMBER, ErrorInfo.CARD_DOES_NOT_EXISTS.toString());
        }
    }
}
