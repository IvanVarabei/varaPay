package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.dao.PaymentDao;
import com.varabei.ivan.model.dao.pool.ConnectionPool;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.User;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class DbPaymentDao extends GenericDao implements PaymentDao {
    private static final String SET_PAYMENT = "insert into payments (sourcecardid, destinationcardid, amount) " +
            "values(?, ?, ?)";

    @Override
    public void makePayment(Long sourceCardId, Long destinationCardId, BigDecimal amount) throws DaoException {
        Connection connection = pool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SET_PAYMENT)) {
            preparedStatement.setLong(PARAM_INDEX_1, sourceCardId);
            preparedStatement.setLong(PARAM_INDEX_2, destinationCardId);
            preparedStatement.setLong(PARAM_INDEX_3, amount.longValue());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DaoException("can not get access to db", e);
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
