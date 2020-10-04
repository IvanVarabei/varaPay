package com.varabei.ivan.model.service;

import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.entity.Account;

import java.util.List;

public interface AccountService {
    void disable(Long accountId) throws ServiceException;
}
