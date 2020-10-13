package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.AccountDao;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.exception.DaoException;

import java.util.List;

public class DbAccountDao extends GenericDao implements AccountDao {
    private static final String CHANGE_ACTIVE_BY_ID = "update accounts set is_active = not is_active where account_id= ?";
    private static final String CREATE_ACCOUNT = "insert into accounts (user_id) values (?)";
    private static final String ABANDON_ACCOUNT = "update accounts set is_abandoned = true where account_id= ?";

    @Override
    public void create(Long userId) throws DaoException {
      //  executeQueryAcceptingOneValue(userId, CREATE_ACCOUNT);
    }

    @Override
    public List<Account> findDisabled() throws DaoException {
        return null;
    }

    @Override
    public void delete(Long accountId) throws DaoException {
       // executeQueryAcceptingOneValue(accountId, ABANDON_ACCOUNT);
    }

    @Override
    public void changeActive(Long accountId) throws DaoException {
        //executeQueryAcceptingOneValue(accountId, CHANGE_ACTIVE_BY_ID);
    }
}
