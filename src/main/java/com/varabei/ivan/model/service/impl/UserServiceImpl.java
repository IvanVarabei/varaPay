package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.service.ServiceException;
import com.varabei.ivan.model.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final UserDao userDao =  DaoFactory.getInstance().getUserDao();
    
    @Override
    public void signIn(String login, String password) throws ServiceException {
        if (login == null || login.isEmpty()) {
            throw new ServiceException("Incorrect login");
        }
        try {
            userDao.ifExists(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void signUp(User user) throws ServiceException {
        try {
            userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        try {
            return userDao.readByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws ServiceException {
        try {
            return userDao.readByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException("", e);
        }
    }
}
