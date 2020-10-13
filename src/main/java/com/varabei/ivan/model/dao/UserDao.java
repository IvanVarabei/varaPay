package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao{
    void create(User user) throws DaoException;

    List<User> findAll() throws DaoException;

    Optional<User> findByLogin(String login)throws DaoException;

    Optional<User> findById(Long id) throws DaoException;

    Optional<User> findByEmail(String email) throws DaoException;

    Optional<User> findByLoginPassword(String login, String password) throws DaoException;
}
