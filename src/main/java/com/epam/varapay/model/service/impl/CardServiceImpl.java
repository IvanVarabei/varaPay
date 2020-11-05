package com.epam.varapay.util;

import com.epam.varapay.model.dao.CardDao;
import com.epam.varapay.model.entity.Card;
import com.epam.varapay.model.exception.DaoException;
import com.epam.varapay.model.dao.DaoFactory;
import com.epam.varapay.model.service.CardService;
import com.epam.varapay.model.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public class CardServiceImpl implements CardService {
    private static CardDao cardDao = DaoFactory.getInstance().getCardDao();

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
