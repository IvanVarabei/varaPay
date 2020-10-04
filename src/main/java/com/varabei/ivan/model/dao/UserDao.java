package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao{
    void create(User user) throws DaoException;

    Optional<User> read(String login)throws DaoException;

    List<User> readAll() throws DaoException;

    boolean ifExists(String login, String password) throws DaoException;
}
