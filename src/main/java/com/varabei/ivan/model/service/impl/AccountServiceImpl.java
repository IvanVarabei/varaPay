package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.dao.AccountDao;
import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.service.ServiceException;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private static UserDao userDao = DaoFactory.getInstance().getUserDao();
    private static AccountDao accountDao = DaoFactory.getInstance().getAccountDao();

    @Override
    public void disable(Long accountId) throws ServiceException {
        try {
            accountDao.disable(accountId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
