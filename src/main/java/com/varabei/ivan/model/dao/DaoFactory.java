package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.dao.impl.DbUserDao;

public final class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();
    private final UserDao userDao = new DbUserDao();

    private DaoFactory() {}

    public UserDao getUserDao() {
        return userDao;
    }

    public static DaoFactory getInstance() {
        return instance;
    }
}