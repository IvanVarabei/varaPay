package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.AccountDao;
import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.dao.pool.ConnectionPool;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbAccountDao extends GenericDao implements AccountDao {
    private static final String DISABLE_ACCOUNT_BY_ID = "update accounts set isactive = false where accountid = ?";

    public void create(User user)throws DaoException {

    }

    @Override
    public void disable(Long accountId) throws DaoException {
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(DISABLE_ACCOUNT_BY_ID);
            preparedStatement.setLong(PARAM_INDEX_1, accountId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(preparedStatement, daoException);
            } finally {
                pool.releaseConnection(connection);
            }
        }
    }
}
