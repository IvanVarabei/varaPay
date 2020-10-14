package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.AccountDao;
import com.varabei.ivan.model.dao.builder.impl.AccountBuilder;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.exception.DaoException;

import java.util.List;

public class AccountDaoImpl extends GenericDao<Account> implements AccountDao {
    private static final String CHANGE_ACTIVE_BY_ID = "update accounts set is_active = not is_active where account_id= ?";
    private static final String CREATE_ACCOUNT = "insert into accounts (user_id) values (?)";
    private static final String ABANDON_ACCOUNT = "update accounts set is_abandoned = true where account_id= ?";
    private static final String FIND_ACCOUNTS_BY_USER_ID =
            "select account_id, balance, is_active, users.user_id, users.login,users.email,\n" +
                    "       users.firstname, users.lastname, users.birth, roles.role_name from accounts\n" +
                    "    join users on accounts.user_id = users.user_id\n" +
                    "    and accounts.user_id = ? and is_abandoned = false\n" +
                    "    join roles on users.role_id = roles.role_id";
    private static final String FIND_DISABLED_ACCOUNTS =
            "select account_id, balance, is_active, users.user_id, users.login,users.email,\n" +
                    "       users.firstname,\n" +
                    "       users.lastname,\n" +
                    "       users.birth,\n" +
                    "       roles.role_name\n" +
                    "from accounts\n" +
                    "         join users on accounts.user_id = users.user_id\n" +
                    "    and not is_active and is_abandoned = false\n" +
                    "         join roles on users.role_id = roles.role_id";

    public AccountDaoImpl() {
        super(new AccountBuilder());
    }

    @Override
    public void create(Long userId) throws DaoException {
        executeUpdate(CREATE_ACCOUNT, userId);
    }

    @Override
    public List<Account> findByUserId(Long userId) throws DaoException {
        return executeQuery(FIND_ACCOUNTS_BY_USER_ID, userId);
    }

    @Override
    public List<Account> findDisabled() throws DaoException {
        return executeQuery(FIND_DISABLED_ACCOUNTS);
    }

    @Override
    public void delete(Long accountId) throws DaoException {
        executeUpdate(ABANDON_ACCOUNT, accountId);
    }

    @Override
    public void changeActive(Long accountId) throws DaoException {
        executeUpdate(CHANGE_ACTIVE_BY_ID, accountId);
    }
}
