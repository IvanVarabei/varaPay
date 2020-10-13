package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.dao.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Optional;

public class GenericDao {
    protected static final ConnectionPool pool = ConnectionPool.getInstance();
    private final Logger log = LogManager.getLogger(GenericDao.class);
    protected static final int PARAM_INDEX_1 = 1;
    protected static final int PARAM_INDEX_2 = 2;
    protected static final int PARAM_INDEX_3 = 3;
    protected static final int PARAM_INDEX_4 = 4;
    protected static final int PARAM_INDEX_5 = 5;
    protected static final int PARAM_INDEX_6 = 6;

    protected void closeResource(AutoCloseable resource, DaoException daoException) throws DaoException {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (Exception closeException) {
            if (daoException != null) {
                daoException.addSuppressed(closeException);
                throw daoException;
            } else {
                log.error("could`t close a resource", closeException);
            }
        }
    }

    protected void closeResource(AutoCloseable resource) throws Exception {
        if (resource != null) {
            resource.close();
        }
    }

    protected void startTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
    }

    protected void endTransaction(Connection connection) throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    protected void cancelTransaction(Connection connection, DaoException daoException) throws DaoException {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            daoException.addSuppressed(e);
            throw daoException;
        }
    }

    protected void executeUpdate(String query, Connection connection, Object... parameters) throws DaoException {
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, parameters);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            closeResource(preparedStatement, daoException);
        }
    }

    protected void executeUpdate(String query, Object... parameters) throws DaoException {
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, parameters);
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

    protected Optional<Long> findLong(String query, Connection connection, String columnLabel, Object ...params)
            throws DaoException {
        return Optional.of(Long.parseLong(String.valueOf(findObject(query, connection, columnLabel, params).get())));
    }

    private Optional<Object> findObject(String query, Connection connection, String columnLabel, Object ...params)
            throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        Optional<Object> accountId = Optional.empty();
        try {
            preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                accountId = Optional.of(resultSet.getObject(columnLabel));
            }
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(resultSet, daoException);
            } finally {
                closeResource(preparedStatement, daoException);
            }
        }
        return accountId;
    }

    private void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            int parameterIndex = i + 1;
            if (parameters[i] != null) {
                statement.setObject(parameterIndex, parameters[i]);
            } else {
                statement.setNull(parameterIndex, Types.NULL);
            }
        }
    }
}
