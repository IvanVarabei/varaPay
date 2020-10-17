package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface AccountDao {
    void create(Long userId)throws DaoException;

    Optional<Account> findById(Long accountId) throws DaoException;

    List<Account> findByUserId(Long userId) throws DaoException;

    List<Account> findDisabled() throws DaoException;

    void changeActive(Long accountId) throws DaoException;

    void delete(Long accountId)throws DaoException;

    Optional<Long> findAccountBalance(Long accountId) throws DaoException;
 }
