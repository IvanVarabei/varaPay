package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.service.ServiceException;
import com.varabei.ivan.model.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public void signIn(String login, String password) throws ServiceException {
        if (login == null || login.isEmpty()) {
            throw new ServiceException("Incorrect login");
        }
        try {
            UserDao userDao = DaoFactory.getInstance().getUserDao();
            userDao.ifExists(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void signOut(String login) throws ServiceException {

    }

    @Override
    public void registration(User user) throws ServiceException {
        try {
            UserDao userDao = DaoFactory.getInstance().getUserDao();
            userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            UserDao userDao = DaoFactory.getInstance().getUserDao();
            return userDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public User find(String login) throws ServiceException {
        try {
            UserDao userDao = DaoFactory.getInstance().getUserDao();
            return userDao.read(login).get();
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }
}
