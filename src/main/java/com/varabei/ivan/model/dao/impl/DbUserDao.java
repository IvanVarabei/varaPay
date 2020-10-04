package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbUserDao extends GenericDao implements UserDao {
    private static final String FIND_ALL_USERS = "select rolename, userid, login, password," +
            " firstname, lastname, email, birth from users join roles on users.roleid = roles.roleid";
    private static final String FIND_ACCOUNTS_BY_USER_ID =
            "select accountid, balance, isactive from accounts where userid = ?";
    private static final String FIND_CARDS_BY_ACCOUNT_ID =
            "select cardid, accountid, cardnumber, validthru, cvc from cards where accountId = ?";
    private static final String FIND_PAYMENTS_BY_CARD_ID =
            "select paymentid, sourcecardid, destinationcardid, amount, paymentinstant from payments " +
                    "where sourcecardid = ? or destinationcardid = ?";
    private static final String FIND_CARD_BY_CARD_ID =
            "select accountid, cardnumber, validthru, cvc from cards where cardid = ?";
    private static final String FIND_USER_BY_LOGIN = "select rolename, userid, login, password, firstname, lastname," +
            " email, birth from users join roles on users.roleid = roles.roleid and login = ?";
    private static final String CREATE_USER = "insert into users(login, password, firstname, lastname, email, birth)" +
            "values (?, ?, ?, ?, ?, ?)";
    private static final String CREATE_ACCOUNT = "insert into accounts(userId) values (?)";
    private static final String FIND_USER_BY_LOGIN_PASSWORD = "select login from users where login = ? and password = ?";

    @Override
    public void create(User user) throws DaoException {
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(CREATE_USER);
            preparedStatement.setString(PARAM_INDEX_1, user.getLogin());
            preparedStatement.setString(PARAM_INDEX_2, user.getPassword());
            preparedStatement.setString(PARAM_INDEX_3, user.getFirstName());
            preparedStatement.setString(PARAM_INDEX_4, user.getLastName());
            preparedStatement.setString(PARAM_INDEX_5, user.getEmail());
            preparedStatement.setObject(PARAM_INDEX_6, user.getBirth());
            preparedStatement.executeUpdate();
            createAccount(user.getLogin(), connection);
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

    @Override
    public Optional<User> read(String login) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            return find(login, connection);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<User> readAll() throws DaoException {
        Connection connection = pool.getConnection();
        try {
            return findAll(connection);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public boolean ifExists(String login, String password) throws DaoException {
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN_PASSWORD);
            preparedStatement.setString(PARAM_INDEX_1, login);
            preparedStatement.setString(PARAM_INDEX_2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(resultSet, daoException);
            } finally {
                try {
                    closeResource(preparedStatement, daoException);
                } finally {
                    pool.releaseConnection(connection);
                }
            }
        }
        return false;
    }

    private void createAccount(String login, Connection connection) throws DaoException {
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(CREATE_ACCOUNT);
            User user = find(login, connection).orElseThrow(DaoException::new);
            preparedStatement.setLong(1, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            daoException = new DaoException("", e);
        } finally {
            closeResource(preparedStatement, daoException);
        }
    }

    private Optional<User> find(String login, Connection connection) throws DaoException {
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        ResultSet resultSet = null;
        Optional<User> user = Optional.empty();
        try {
            preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN);
            preparedStatement.setString(PARAM_INDEX_1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = Optional.of(instantiateUser(resultSet, connection));
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
        return user;
    }

    private List<User> findAll(Connection connection) throws DaoException {
        List<User> users = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL_USERS);
            while (resultSet.next()) {
                users.add(instantiateUser(resultSet, connection));
            }
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(resultSet, daoException);
            } finally {
                closeResource(statement, daoException);
            }
        }
        return users;
    }

    private List<Account> findAllAccountsByUserId(Long userId, Connection connection) throws DaoException {
        List<Account> accounts = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_ACCOUNTS_BY_USER_ID);
            preparedStatement.setLong(PARAM_INDEX_1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                Long accountId = resultSet.getLong("accountId");
                account.setCards(findCardsByAccountId(accountId, connection));
                account.setAccountId(accountId);
                account.setBalance(new BigDecimal(resultSet.getLong("balance")));
                account.setActive(resultSet.getBoolean("isactive"));
                accounts.add(account);
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
        return accounts;
    }

    private List<Card> findCardsByAccountId(Long accountId, Connection connection) throws DaoException {
        List<Card> cards = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_CARDS_BY_ACCOUNT_ID);
            preparedStatement.setLong(PARAM_INDEX_1, accountId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Card card = new Card();
                Long cardId = resultSet.getLong("cardId");
                card.setPayments(findPaymentsByCardId(cardId, connection));
                card.setCardId(cardId);
                card.setCardNumber(resultSet.getString("cardNumber"));
                card.setValidThruDate(LocalDate.parse(resultSet.getString("validThru")));
                card.setCvc(resultSet.getString("cvc"));
                cards.add(card);
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
        return cards;
    }

    private List<Payment> findPaymentsByCardId(Long cardId, Connection connection) throws DaoException {
        List<Payment> payments = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_PAYMENTS_BY_CARD_ID);
            preparedStatement.setLong(PARAM_INDEX_1, cardId);
            preparedStatement.setLong(PARAM_INDEX_2, cardId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(resultSet.getLong("paymentId"));
                payment.setSourceCardInfo(findCardInfoByCardId(
                        resultSet.getLong("sourceCardId"), connection));
                payment.setDestinationCardInfo(findCardInfoByCardId(
                        resultSet.getLong("destinationCardId"), connection));
                payment.setAmount(new BigDecimal(resultSet.getLong("amount")));
                payment.setPaymentInstant(resultSet.getTimestamp("paymentInstant").toLocalDateTime());
                payments.add(payment);
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
        return payments;
    }

    private CardInfo findCardInfoByCardId(Long cardId, Connection connection) throws DaoException {
        CardInfo cardInfo = new CardInfo();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(FIND_CARD_BY_CARD_ID);
            preparedStatement.setLong(PARAM_INDEX_1, cardId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cardInfo.setCardId(cardId);
                cardInfo.setCardNumber(resultSet.getString("cardnumber"));
                cardInfo.setValidThruDate(LocalDate.parse(resultSet.getString("validThru")));
                cardInfo.setCvc(resultSet.getString("cvc"));
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
        return cardInfo;
    }

    private User instantiateUser(ResultSet resultSet, Connection connection) throws SQLException, DaoException {
        User user = new User();
        Long userId = resultSet.getLong("userId");
        List<Account> userAccounts = findAllAccountsByUserId(userId, connection);
        userAccounts.forEach(account -> account.setOwner(user));
        user.setAccounts(userAccounts);
        user.setUserId(userId);
        user.setRole(resultSet.getString("roleName"));
        user.setLogin(resultSet.getString("login"));
        user.setFirstName(resultSet.getString("firstname"));
        user.setLastName(resultSet.getString("lastname"));
        user.setEmail(resultSet.getString("email"));
        user.setBirth(LocalDate.parse(resultSet.getString("birth")));
        return user;
    }
}
