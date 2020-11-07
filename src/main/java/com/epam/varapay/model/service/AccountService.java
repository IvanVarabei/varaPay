package com.epam.varapay.model.service;

import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.entity.Account;

import java.util.List;
import java.util.Optional;

/**
 * @author Ivan Varabei
 * @version 1.0
 */
public interface AccountService {
    void create(Long userId) throws ServiceException;

    Optional<Account> findById(Long accountId) throws ServiceException;

    List<Account> findByUserLogin(String login) throws ServiceException;

    List<Account> findDisabledByLoginOrAccountId(String query) throws ServiceException;

    void changeActive(Long accountId) throws ServiceException;

    Optional<String > delete(Long accountId) throws ServiceException;
}
