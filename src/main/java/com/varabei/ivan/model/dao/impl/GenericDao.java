package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.dao.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
}
