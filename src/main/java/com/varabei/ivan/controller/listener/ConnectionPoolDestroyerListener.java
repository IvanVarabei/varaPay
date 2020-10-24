package com.varabei.ivan.controller.listener;

import com.varabei.ivan.model.entity.Currency;
import com.varabei.ivan.model.pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConnectionPoolDestroyerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Currency.values();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
    }
}
