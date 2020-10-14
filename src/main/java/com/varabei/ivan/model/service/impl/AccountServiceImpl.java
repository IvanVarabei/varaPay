package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.dao.AccountDao;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.exception.ServiceException;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private static AccountDao accountDao = DaoFactory.getInstance().getAccountDao();

    @Override
    public void create(Long userId) throws ServiceException {
        try {
            accountDao.create(userId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Account> findByUserId(Long userId) throws ServiceException {
        try {
            return accountDao.findByUserId(userId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Account> findDisabled() throws ServiceException {
        try {
            return accountDao.findDisabled();
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
    public void delete(Long accountId) throws ServiceException {
        try {
            accountDao.delete(accountId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
