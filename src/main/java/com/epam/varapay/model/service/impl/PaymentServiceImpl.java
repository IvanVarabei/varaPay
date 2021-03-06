package com.epam.varapay.model.service.impl;

import com.epam.varapay.model.dao.CardDao;
import com.epam.varapay.model.dao.DaoFactory;
import com.epam.varapay.model.dao.PaymentDao;
import com.epam.varapay.model.entity.Card;
import com.epam.varapay.model.entity.Payment;
import com.epam.varapay.exception.DaoException;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.DataTransferMapKey;
import com.epam.varapay.model.service.ErrorMessage;
import com.epam.varapay.model.service.PaymentService;
import com.epam.varapay.model.validator.PaymentValidator;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {
    private static PaymentValidator paymentValidator = PaymentValidator.getInstance();
    private PaymentDao paymentDao;
    private CardDao cardDao;
    private static final String SPACE_BETWEEN_CARD_NUMBER_FIGURES = " ";

    public PaymentServiceImpl(){
        init();
    }

    /**
     * The method is necessary to prevent connection pool initialization during testing.
     * Testing class can have inner class which overrides this method.
     */
    protected void init(){
        paymentDao = DaoFactory.getInstance().getPaymentDao();
        cardDao = DaoFactory.getInstance().getCardDao();
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
                String destinationCardNumber = paymentData.get(DataTransferMapKey.CARD_NUMBER)
                        .replace(SPACE_BETWEEN_CARD_NUMBER_FIGURES, Strings.EMPTY);
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
            paymentData.put(DataTransferMapKey.CVC, ErrorMessage.CVC.toString());
        }
        if (sourceCard.getAccount().getBalance().compareTo(amount) < 0) {
            paymentData.put(DataTransferMapKey.AMOUNT, ErrorMessage.NOT_ENOUGH_BALANCE.toString());
        }
        if (!sourceCard.getAccount().isActive()) {
            paymentData.put(DataTransferMapKey.CARD_ID, ErrorMessage.SOURCE_ACCOUNT_BLOCKED.toString());
        }
    }

    private void checkDestinationCard(Map<String, String> paymentData) throws DaoException {
        String destinationCardNumber = paymentData.get(DataTransferMapKey.CARD_NUMBER)
                .replace(SPACE_BETWEEN_CARD_NUMBER_FIGURES, Strings.EMPTY);
        YearMonth validThru = YearMonth.parse(paymentData.get(DataTransferMapKey.VALID_THRU));
        Optional<Card> destinationCard = cardDao.findByCardNumberAndValidThru(destinationCardNumber, validThru);
        if (destinationCard.isPresent()) {
            if (!destinationCard.get().getAccount().isActive()) {
                paymentData.put(DataTransferMapKey.CARD_NUMBER, ErrorMessage.DESTINATION_ACCOUNT_BLOCKED.toString());
            }
        } else {
            paymentData.put(DataTransferMapKey.CARD_NUMBER, ErrorMessage.CARD_DOES_NOT_EXISTS.toString());
        }
    }
}
