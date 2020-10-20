package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.dao.builder.IdentifiableBuilder;
import com.varabei.ivan.model.pool.ConnectionPool;
import com.varabei.ivan.model.entity.Identifiable;
import com.varabei.ivan.model.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenericDao<T extends Identifiable> {
    private static final Logger log = LogManager.getLogger(GenericDao.class);
    protected static final ConnectionPool pool = ConnectionPool.getInstance();
    private final IdentifiableBuilder<T> builder;

    protected GenericDao(IdentifiableBuilder<T> builder) {
        this.builder = builder;
    }

    protected void closeResource(AutoCloseable resource, DaoException daoException) throws DaoException {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (Exception closeException) {
            if (daoException != null) {
                daoException.addSuppressed(closeException);
            } else {
                log.error("could`t close a resource", closeException);
            }
        }
        if (daoException != null) {
            throw daoException;
        }
    }

    protected void startTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    }

    protected void endTransaction(Connection connection) throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
    }

    protected void cancelTransaction(Connection connection, DaoException daoException) throws DaoException {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            daoException.addSuppressed(e);
        }
        throw daoException;
    }

    protected void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            int parameterIndex = i + 1;
            if (parameters[i] != null) {
                statement.setObject(parameterIndex, parameters[i]);
            } else {
                statement.setNull(parameterIndex, Types.NULL);
            }
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
        try {
            executeUpdate(query, connection, parameters);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    protected List<T> executeQuery(String query, Connection connection, Object... parameters) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        List<T> entities = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, parameters);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T entity = builder.build(resultSet);
                entities.add(entity);
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
        return entities;
    }

    protected List<T> executeQuery(String query, Object... parameters) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            return executeQuery(query, connection, parameters);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    protected Optional<T> executeForSingleResult(String query, Connection connection, Object... parameters)
            throws DaoException {
        List<T> items = executeQuery(query, connection, parameters);
        return (items.size() == 1) ? Optional.of(items.get(0)) : Optional.empty();
    }

    protected Optional<T> executeForSingleResult(String query, Object... parameters) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            return executeForSingleResult(query, connection, parameters);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    protected Optional<Long> findLong(String query, Connection connection, String columnLabel, Object... params)
            throws DaoException {
        Optional<Object> foundValue = findObject(query, connection, columnLabel, params);
        return foundValue.map(o -> Long.parseLong(String.valueOf(o)));
    }

    protected Optional<String> findString(String query, Connection connection, String columnLabel, Object... params)
            throws DaoException {
        Optional<Object> foundValue = findObject(query, connection, columnLabel, params);
        return foundValue.map(String::valueOf);
    }

    protected boolean findBoolean(String query, String columnLabel, Object... params)
            throws DaoException {
        Connection connection = pool.getConnection();
        try {
            Optional<Object> foundValue = findObject(query, connection, columnLabel, params);
            return (boolean) foundValue.get();
        } finally {
            pool.releaseConnection(connection);
        }
    }

    protected Optional<Long> findLong(String query, String columnLabel, Object... params)
            throws DaoException {
        Connection connection = pool.getConnection();
        try {
            Optional<Object> foundValue = findObject(query, connection, columnLabel, params);
            return foundValue.map(o -> Long.parseLong(String.valueOf(o)));
        } finally {
            pool.releaseConnection(connection);
        }
    }

    private Optional<Object> findObject(String query, Connection connection, String columnLabel, Object... params)
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
}