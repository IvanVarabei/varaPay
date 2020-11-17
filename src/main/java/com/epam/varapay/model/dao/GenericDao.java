package com.epam.varapay.model.dao;

import com.epam.varapay.exception.DaoException;
import com.epam.varapay.model.dao.builder.IdentifiableBuilder;
import com.epam.varapay.model.entity.Identifiable;
import com.epam.varapay.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenericDao<T extends Identifiable> {
    private static final Logger log = LogManager.getLogger(GenericDao.class);
    protected ConnectionPool pool;
    private final IdentifiableBuilder<T> builder;

    protected GenericDao(IdentifiableBuilder<T> builder) {
        this.builder = builder;
        init();
    }

    /**
     * The method is necessary to prevent connection pool initialization during testing.
     * Testing class can have inner class which overrides this method.
     */
    protected void init() {
        pool = ConnectionPool.getInstance();
    }

    protected void closeResource(AutoCloseable resource) {
        try {
            resource.close();
        } catch (Exception closeException) {
            log.error("could`t close a resource", closeException);
        }
    }

    protected void startRepeatableReadTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
    }

    protected void startSerializableTransaction(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
    }

    protected void endTransaction(Connection connection) throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
    }

    protected void cancelTransaction(Connection connection) {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            log.error("could`t rollback", e);
        }
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
        try {
            preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, parameters);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("can not get access to db", e);
        } finally {
            closeResource(preparedStatement);
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
            throw new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(resultSet);
            } finally {
                closeResource(preparedStatement);
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

    protected Optional<String> findString(String query, Connection connection, String columnLabel, Object... params)
            throws DaoException {
        Optional<Object> foundValue = findObject(query, connection, columnLabel, params);
        return foundValue.map(String::valueOf);
    }

    protected Optional<String> findString(String query, String columnLabel, Object... params) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            return findString(query, connection, columnLabel, params);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    protected Optional<Long> findLong(String query, Connection connection, String columnLabel, Object... params)
            throws DaoException {
        Optional<Object> foundValue = findObject(query, connection, columnLabel, params);
        return foundValue.map(o -> Long.parseLong(String.valueOf(o)));
    }

    protected Optional<Long> findLong(String query, String columnLabel, Object... params) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            Optional<Object> foundValue = findObject(query, connection, columnLabel, params);
            return foundValue.map(o -> Long.parseLong(String.valueOf(o)));
        } finally {
            pool.releaseConnection(connection);
        }
    }

    protected boolean findBoolean(String query, String columnLabel, Object... params) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            return (boolean) findObject(query, connection, columnLabel, params).orElseThrow(DaoException::new);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    private Optional<Object> findObject(String query, Connection connection, String columnLabel, Object... params)
            throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Optional<Object> soughtObject = Optional.empty();
        try {
            preparedStatement = connection.prepareStatement(query);
            setParameters(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                soughtObject = Optional.of(resultSet.getObject(columnLabel));
            }
        } catch (SQLException e) {
            throw new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(resultSet);
            } finally {
                closeResource(preparedStatement);
            }
        }
        return soughtObject;
    }
}
