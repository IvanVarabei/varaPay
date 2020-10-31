package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.DaoException;

import java.util.Optional;

public interface UserDao {
    void create(User user, String hashedPassword, String salt, String secretWord) throws DaoException;

    Optional<User> findById(Long id) throws DaoException;

    Optional<User> findByLogin(String login) throws DaoException;

    Optional<User> findByEmail(String email) throws DaoException;

    Optional<String> findPasswordByLogin(String login) throws DaoException;

    Optional<String> findSaltByLogin(String login) throws DaoException;

    boolean isAuthenticSecretWord(Long accountId, String secretWord) throws DaoException;

    void updatePassword(String email, String newPassword, String newSalt) throws DaoException;
}
