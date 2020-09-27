package com.varabei.ivan.model.dao.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {
    private static final String URL = "jdbc:postgresql://localhost:5432/bank";
    private static final int DEFAULT_POOL_SIZE = 5;
    private static final String POSTGRES_DRIVER_CLASS = "org.postgresql.Driver";
    private static final String CONNECTION_PROPERTIES_FILE = "postgresConnection.properties";
    private final Logger log = LogManager.getLogger(ConnectionPool.class);
    private final BlockingQueue<ProxyConnection> freeConnection;
    private final Queue<ProxyConnection> givenAwayConnections;

    private static class PoolHolder {
        private static final ConnectionPool POOL_INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return PoolHolder.POOL_INSTANCE;
    }

    private ConnectionPool() {
        freeConnection = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>();
        Properties properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream(CONNECTION_PROPERTIES_FILE);
        try {
            properties.load(is);
            Class.forName(POSTGRES_DRIVER_CLASS);
            for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                freeConnection.add(new ProxyConnection(DriverManager.getConnection(URL, properties)));
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            log.fatal(e);
            throw new ConnectionPoolException("can't initialize connection pool", e);
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            log.error(e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection.getClass() == ProxyConnection.class) {
            givenAwayConnections.remove(connection);
            freeConnection.add((ProxyConnection) connection);
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnection.take().reallyClose();
            } catch (InterruptedException e) {
                log.error(e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
