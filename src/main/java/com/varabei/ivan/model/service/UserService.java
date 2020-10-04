package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.User;

import java.util.List;

public interface UserService {

    void registration(User user) throws ServiceException;

    List<User> findAll() throws ServiceException;
    void signIn(String login, String password) throws ServiceException;
    void signOut(String login) throws ServiceException;

    User find(String login) throws ServiceException;
}
