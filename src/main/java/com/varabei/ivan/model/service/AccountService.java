package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.exception.ServiceException;

import java.util.List;

public interface AccountService {
    void create(Long userId)throws ServiceException;

    List<Account> findDisabled() throws ServiceException;

    void changeActive(Long accountId) throws ServiceException;

    void delete(Long accountId)throws ServiceException;
}
