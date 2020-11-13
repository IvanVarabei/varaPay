package com.epam.varapay.model.dao.impl;

import com.epam.varapay.model.dao.AccountDao;
import com.epam.varapay.model.dao.ColumnLabel;
import com.epam.varapay.model.dao.GenericDao;
import com.epam.varapay.model.dao.builder.impl.AccountBuilder;
import com.epam.varapay.model.entity.Account;
import com.epam.varapay.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountDaoImpl extends GenericDao<Account> implements AccountDao {
    private static final String CHANGE_ACTIVE_BY_ID = "update accounts set is_active = not is_active " +
            "where account_id= ?";
    private static final String CREATE_ACCOUNT = "insert into accounts (user_id) values (?)";
    private static final String ABANDON_ACCOUNT = "update accounts set is_abandoned = true where account_id= ?";
    private static final String ABANDON_CARDS_BY_ACCOUNT_ID = "update cards set is_abandoned = true " +
            "where account_id= ?";
    private static final String FIND_ACCOUNTS_BY_LOGIN =
            "select account_id, balance, is_active, users.user_id, users.login, password, salt,users.email,\n" +
                    "       users.firstname, users.lastname, users.birth, roles.role_name from accounts\n" +
                    "    join users on accounts.user_id = users.user_id\n" +
                    "    and login = ? and is_abandoned = false\n" +
                    "    join roles on users.role_id = roles.role_id";
    private static final String FIND_ACCOUNT_BY_ID =
            "select account_id, balance, is_active, users.user_id, users.login, password, salt,users.email,\n" +
                    "       users.firstname, users.lastname, users.birth, roles.role_name from accounts\n" +
                    "    join users on accounts.user_id = users.user_id\n" +
                    "    and accounts.account_id = ? and is_abandoned = false\n" +
                    "    join roles on users.role_id = roles.role_id";
    private static final String FIND_DISABLED_BY_ACCOUNT_ID =
            "select account_id,\n" +
                    "       balance,\n" +
                    "       is_active,\n" +
                    "       users.user_id,\n" +
                    "       users.login,\n" +
                    "       password,\n" +
                    "       salt,\n" +
                    "       users.email,\n" +
                    "       users.firstname,\n" +
                    "       users.lastname,\n" +
                    "       users.birth,\n" +
                    "       roles.role_name\n" +
                    "from accounts\n" +
                    "         join users on accounts.user_id = users.user_id\n" +
                    "    and not is_active and is_abandoned = false and account_id = ?\n" +
                    "         join roles on users.role_id = roles.role_id";
    private static final String FIND_DISABLED_BY_LOGIN =
            "select account_id,\n" +
                    "       balance,\n" +
                    "       is_active,\n" +
                    "       users.user_id,\n" +
                    "       users.login,\n" +
                    "       password,\n" +
                    "       salt,\n" +
                    "       users.email,\n" +
                    "       users.firstname,\n" +
                    "       users.lastname,\n" +
                    "       users.birth,\n" +
                    "       roles.role_name\n" +
                    "from accounts\n" +
                    "         join users on accounts.user_id = users.user_id\n" +
                    "    and not is_active and not is_abandoned and login = ?\n" +
                    "         join roles on users.role_id = roles.role_id";
    private static final String FIND_ACCOUNT_BALANCE_BY_ID = "select balance from accounts where account_id = ?";

    public AccountDaoImpl() {
        super(new AccountBuilder());
    }

    @Override
    public void create(Long userId) throws DaoException {
        executeUpdate(CREATE_ACCOUNT, userId);
    }

    @Override
    public Optional<Account> findById(Long accountId) throws DaoException {
        return executeForSingleResult(FIND_ACCOUNT_BY_ID, accountId);
    }

    @Override
    public List<Account> findByUserLogin(String login) throws DaoException {
        return executeQuery(FIND_ACCOUNTS_BY_LOGIN, login);
    }

    @Override
    public List<Account> findDisabledByLogin(String login) throws DaoException {
        return executeQuery(FIND_DISABLED_BY_LOGIN, login);
    }

    @Override
    public List<Account> findDisabledByAccountId(Long accountId) throws DaoException {
        return executeQuery(FIND_DISABLED_BY_ACCOUNT_ID, accountId);
    }

    @Override
    public Optional<Long> findAccountBalance(Long accountId) throws DaoException {
        return findLong(FIND_ACCOUNT_BALANCE_BY_ID, ColumnLabel.BALANCE, accountId);
    }

    @Override
    public void changeActive(Long accountId) throws DaoException {
        executeUpdate(CHANGE_ACTIVE_BY_ID, accountId);
    }

    @Override
    public void delete(Long accountId) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            startTransaction(connection);
            executeUpdate(ABANDON_ACCOUNT, connection, accountId);
            executeUpdate(ABANDON_CARDS_BY_ACCOUNT_ID, connection, accountId);
            endTransaction(connection);
        } catch (SQLException e) {
            cancelTransaction(connection);
            throw new DaoException("can not get access to db", e);
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
