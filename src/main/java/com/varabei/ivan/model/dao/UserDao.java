package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao{
    void create(User user) throws DaoException;

    Optional<User> readByLogin(String login)throws DaoException;

    Optional<User> readById(Long id) throws DaoException;

    Optional<User> readByEmail(String email) throws DaoException;

    List<User> readAll() throws DaoException;

    Optional<User> findByLoginPassword(String login, String password) throws DaoException;
}
