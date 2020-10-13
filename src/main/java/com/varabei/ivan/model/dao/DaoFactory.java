package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.dao.impl.*;

public final class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private final UserDao userDao = new DbUserDao();
    private final AccountDao accountDao = new DbAccountDao();
    private final PaymentDao paymentDao = new DbPaymentDao();
    private final CardDao cardDao = new DbCardDao();
    private final BidDao bidDao = new DbBidDao();

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
    public BidDao getTopUpBidDao() {
        return bidDao;
    }

    public static DaoFactory getInstance() {
        return instance;
    }

}