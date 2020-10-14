package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.exception.DaoException;

import java.util.List;

public interface AccountDao {
    void create(Long userId)throws DaoException;

    List<Account> findByUserId(Long userId) throws DaoException;

    List<Account> findDisabled() throws DaoException;

    void changeActive(Long accountId) throws DaoException;

    void delete(Long accountId)throws DaoException;
}
