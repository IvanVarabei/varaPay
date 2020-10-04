package com.varabei.ivan.model.dao;

public interface AccountDao {
    void disable(Long accountId) throws DaoException;
}
