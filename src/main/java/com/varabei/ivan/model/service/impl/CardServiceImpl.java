package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.dao.CardDao;
import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.service.CardService;
import com.varabei.ivan.model.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public class CardServiceImpl implements CardService {
    private static final CardDao cardDao = DaoFactory.getInstance().getCardDao();
    private static final int AMOUNT_OF_FIGURES_IN_CARD_NUMBER = 16;
    private static final String REGEX_NOT_DIGIT = "\\D+";

    @Override
    public String createCardAndReturnCvc(Long accountId) throws ServiceException {
        try {
            return cardDao.createCardAndReturnCvc(accountId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public Optional<Card> findById(Long id) throws ServiceException {
        try {
            return cardDao.findById(id);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
//
//    @Override
//    public Optional<Card> findByCardNumber(String cardNumber) throws ServiceException {
//        try {
//            String clearNumber = cardNumber.replaceAll(REGEX_NOT_DIGIT, "");
//            if (clearNumber.length() == AMOUNT_OF_FIGURES_IN_CARD_NUMBER) {
//                return cardDao.findByCardNumber(clearNumber);
//            }
//            throw new ServiceException();
//        } catch (DaoException daoException) {
//            throw new ServiceException(daoException);
//        }
//    }

    @Override
    public List<Card> findByAccountId(Long accountId) throws ServiceException {
        try {
            return cardDao.findByAccountId(accountId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void delete(Long cardId) throws ServiceException {
        try {
            cardDao.delete(cardId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
