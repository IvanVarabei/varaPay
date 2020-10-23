package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.UserService;
import com.varabei.ivan.util.CustomSecurity;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserDao userDao = DaoFactory.getInstance().getUserDao();
    private static final int SALT_LENGTH = 64;

    @Override
    public void signUp(User user, String password, String secretWord) throws ServiceException {
        try {
            String salt = CustomSecurity.generateRandom(SALT_LENGTH);
            password = CustomSecurity.generateHash(password + salt);
            secretWord = CustomSecurity.generateHash(secretWord);
            userDao.create(user, password, salt, secretWord);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        try {
            Optional<String> hashedPassword = userDao.findPasswordByLogin(login);
            Optional<String> salt = userDao.findSaltByLogin(login);
            if (hashedPassword.isPresent() && salt.isPresent() &&
                    hashedPassword.get().equals(CustomSecurity.generateHash(password + salt.get()))) {
                return userDao.findByLogin(login);
            }
            return Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updatePassword(String email, String newPassword) throws ServiceException {
        try {
            String salt = CustomSecurity.generateRandom(SALT_LENGTH);
            String hashedPassword = CustomSecurity.generateHash(newPassword + salt);
            userDao.updatePassword(email, hashedPassword, salt);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isAuthenticSecretWord(String login, String secretWord) throws ServiceException {
        try {
            secretWord = CustomSecurity.generateHash(secretWord);
            return userDao.isAuthenticSecretWord(login, secretWord);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        try {
            return userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) throws ServiceException {
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws ServiceException {
        try {
            return userDao.findByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}