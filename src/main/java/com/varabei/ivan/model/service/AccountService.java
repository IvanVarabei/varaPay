package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    void create(Long userId) throws ServiceException;

    Optional<Account> findById(Long accountId) throws ServiceException;

    List<Account> findByUserLogin(String login) throws ServiceException;

    List<Account> findDisabledByLoginOrAccountId(String query) throws ServiceException;

    void changeActive(Long accountId) throws ServiceException;

    void delete(Long accountId) throws ServiceException;
}
