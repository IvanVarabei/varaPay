package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserDao{
    void create(User user, String hashedPassword, String salt, String secretWord) throws DaoException;

    List<User> findAll() throws DaoException;

    Optional<User> findByLogin(String login)throws DaoException;

    Optional<User> findById(Long id) throws DaoException;

    Optional<User> findByEmail(String email) throws DaoException;

    Optional<String> findPasswordById(Long id) throws DaoException;

    Optional<String> findPasswordByLogin(String login) throws DaoException;

    Optional<String> findSaltById(Long id) throws DaoException;

    Optional<String> findSaltByLogin(String login) throws DaoException;

    void updatePassword(String email, String newPassword, String newSalt) throws DaoException;

    void updatePassword(Long id, String newPassword, String newSalt) throws DaoException;

    boolean checkPresenceByIdPassword(Long id, String password) throws DaoException;

    boolean isAuthenticSecretWord(String login, String secretWord) throws DaoException;
}
