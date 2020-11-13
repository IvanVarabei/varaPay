package com.epam.varapay.model.service.impl;

import com.epam.varapay.exception.DaoException;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.dao.AccountDao;
import com.epam.varapay.model.dao.BidDao;
import com.epam.varapay.model.dao.DaoFactory;
import com.epam.varapay.model.entity.Account;
import com.epam.varapay.model.service.AccountService;
import com.epam.varapay.model.service.ErrorMessage;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class AccountServiceImpl implements AccountService {
    private static AccountDao accountDao = DaoFactory.getInstance().getAccountDao();
    private static BidDao bidDao = DaoFactory.getInstance().getTopUpBidDao();
    static final Pattern DIGIT = Pattern.compile("\\d+");

    @Override
    public void create(Long userId) throws ServiceException {
        try {
            accountDao.create(userId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public Optional<Account> findById(Long accountId) throws ServiceException {
        try {
            return accountDao.findById(accountId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Account> findByUserLogin(String login) throws ServiceException {
        try {
            return accountDao.findByUserLogin(login);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Account> findDisabledByLoginOrAccountId(String query) throws ServiceException {
        try {
            if (DIGIT.matcher(query).find()) {
                return accountDao.findDisabledByAccountId(Long.parseLong(query));
            } else {
                return accountDao.findDisabledByLogin(query);
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void changeActive(Long accountId) throws ServiceException {
        try {
            accountDao.changeActive(accountId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public Optional<String> delete(Long accountId) throws ServiceException {
        try {
            Optional<Long> balance = accountDao.findAccountBalance(accountId);
            if (balance.isEmpty() || balance.get() > 0) {
                return Optional.of(ErrorMessage.BALANCE_NOT_EMPTY.toString());
            }
            if (bidDao.isPresentInProgressBids(accountId)) {
                return Optional.of(ErrorMessage.IN_PROGRESS_BIDS.toString());
            }
            accountDao.delete(accountId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
        return Optional.empty();
    }
}
