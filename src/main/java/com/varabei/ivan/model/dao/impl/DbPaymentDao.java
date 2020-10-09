package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.dao.PaymentDao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbPaymentDao extends GenericDao implements PaymentDao {
    private static final String SET_PAYMENT = "insert into payments (sourcecardid, destinationcardid, amount) " +
            "values(?, ?, ?)";
    private static final String FIND_CARD_ID_BY_NUMBER = "select cardId from cards where cardnumber = ?";
    private static final String FIND_ACCOUNT_ID_BY_CARD_ID = "select accountId from cards where cardId = ?";
    private static final String SUBTRACT_ACCOUNT_BALANCE = "update accounts set balance = balance - ? where accountid = ?";
    private static final String ADD_ACCOUNT_BALANCE = "update accounts set balance = balance + ? where accountid = ?";

    @Override
    public void makePayment(Long sourceCardId, String destinationCardNumber, BigDecimal amount) throws DaoException {
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            preparedStatement = connection.prepareStatement(SET_PAYMENT);
            Long destinationCardId = findCardIdByNumber(destinationCardNumber, connection);
            Long sourceAccountId = findAccountIdByCardId(sourceCardId, connection);
            Long destAccountId = findAccountIdByCardId(destinationCardId, connection);
            changeAccountBalance(sourceAccountId, amount.longValue(), connection, SUBTRACT_ACCOUNT_BALANCE);
            changeAccountBalance(destAccountId, amount.longValue(), connection, ADD_ACCOUNT_BALANCE);
            preparedStatement.setLong(PARAM_INDEX_1, sourceCardId);
            preparedStatement.setLong(PARAM_INDEX_2, destinationCardId);
            preparedStatement.setLong(PARAM_INDEX_3, amount.longValue());
            preparedStatement.execute();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                daoException.addSuppressed(throwables);
            }
        } finally {
            try {
                closeResource(preparedStatement, daoException);
            } finally {
                pool.releaseConnection(connection);
            }
        }
    }

    private Long findCardIdByNumber(String number, Connection connection) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_CARD_ID_BY_NUMBER);
            preparedStatement.setString(PARAM_INDEX_1, number);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong("cardId");
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(resultSet, daoException);
            } finally {
                closeResource(preparedStatement, daoException);
            }
        }
        throw new DaoException("not found");
    }

    private Long findAccountIdByCardId(Long cardId, Connection connection) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_ACCOUNT_ID_BY_CARD_ID);
            preparedStatement.setLong(PARAM_INDEX_1, cardId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getLong("accountId");
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(resultSet, daoException);
            } finally {
                closeResource(preparedStatement, daoException);
            }
        }
        throw new DaoException("not found");
    }

    private void changeAccountBalance(Long accountID, Long amount, Connection connection, String query) throws DaoException {
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(PARAM_INDEX_1, amount);
            preparedStatement.setLong(PARAM_INDEX_2, accountID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            closeResource(preparedStatement, daoException);
        }
    }
}
