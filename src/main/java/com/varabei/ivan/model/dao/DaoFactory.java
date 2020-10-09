package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.dao.impl.DbAccountDao;
import com.varabei.ivan.model.dao.impl.DbCardDao;
import com.varabei.ivan.model.dao.impl.DbPaymentDao;
import com.varabei.ivan.model.dao.impl.DbUserDao;

public final class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private final UserDao userDao = new DbUserDao();
    private final AccountDao accountDao = new DbAccountDao();
    private final PaymentDao paymentDao = new DbPaymentDao();
    private final CardDao cardDao = new DbCardDao();

    private DaoFactory() {}

    public UserDao getUserDao() {
        return userDao;
    }
    public AccountDao getAccountDao() {
        return accountDao;
    }
    public PaymentDao getPaymentDao() {
        return paymentDao;
    }
    public CardDao getCardDao() {
        return cardDao;
    }

    public static DaoFactory getInstance() {
        return instance;
    }

}