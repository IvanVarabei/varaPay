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

    @Override
    public void create(Long accountId) throws ServiceException {
        try {
            cardDao.create(accountId);
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
