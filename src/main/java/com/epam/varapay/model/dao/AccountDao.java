package com.epam.varapay.model.dao;

import com.epam.varapay.model.entity.Account;
import com.epam.varapay.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface AccountDao {
    void create(Long userId) throws DaoException;

    Optional<Account> findById(Long accountId) throws DaoException;

    List<Account> findByUserLogin(String login) throws DaoException;

    List<Account> findDisabledByLogin(String login) throws DaoException;

    List<Account> findDisabledByAccountId(Long accountId) throws DaoException;

    Optional<Long> findAccountBalance(Long accountId) throws DaoException;

    void changeActive(Long accountId) throws DaoException;

    void delete(Long accountId) throws DaoException;
}
