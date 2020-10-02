package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.service.ServiceException;
import com.varabei.ivan.model.service.UserService;

import java.util.List;

public class DefaultUserService implements UserService {
    private static UserDao userDao = DaoFactory.getInstance().getUserDao();

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }
}
