package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.dao.impl.*;

public final class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private final UserDao userDao = new UserDaoImpl();
    private final AccountDao accountDao = new AccountDaoImpl();
    private final PaymentDao paymentDao = new PaymentDaoImpl();
    private final CardDao cardDao = new CardDaoImpl();
    private final BidDao bidDao = new BidDaoImpl();
    private final CurrencyDao currencyDao = new CurrencyDaoImpl();

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        return instance;
    }

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

    public CurrencyDao getCurrencyDao() {
        return currencyDao;
    }
}