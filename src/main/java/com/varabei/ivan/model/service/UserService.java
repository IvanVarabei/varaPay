package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void signUp(User user) throws ServiceException;

    List<User> findAll() throws ServiceException;

    void signIn(String login, String password) throws ServiceException;

    Optional<User> findByLogin(String login) throws ServiceException;

    Optional<User> findByEmail(String email) throws ServiceException;
}
