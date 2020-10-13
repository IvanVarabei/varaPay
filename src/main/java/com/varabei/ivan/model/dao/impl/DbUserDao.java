package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbUserDao extends GenericDao implements UserDao {
    private static final String FIND_ALL_USERS = "select role_name, user_id, login, password," +
            " firstname, lastname, email, birth from users join roles on users.role_id = roles.role_id";
    private static final String FIND_ACCOUNTS_BY_USER_ID =
            "select account_id, balance, is_active from accounts where user_id = ? and is_abandoned = false";
    private static final String FIND_CARDS_BY_ACCOUNT_ID =
            "select card_id, account_id, card_number, valid_thru, cvc from cards where account_id= ? and is_abandoned = false";
    private static final String FIND_PAYMENTS_BY_CARD_ID =
            "select payment_id, source_card_id, destination_card_id, amount, payment_instant from payments " +
                    "where source_card_id = ? or destination_card_id = ?";
    private static final String FIND_CARD_BY_CARD_ID =
            "select account_id, card_number, valid_thru, cvc from cards where card_id = ?";
    private static final String FIND_USER_BY_ID = "select role_name, user_id, login, password, firstname, lastname," +
            " email, birth from users join roles on users.role_id = roles.role_id and user_id = ?";
    private static final String FIND_USER_BY_LOGIN = "select role_name, user_id, login, password, firstname, lastname," +
            " email, birth from users join roles on users.role_id = roles.role_id and login = ?";
    private static final String FIND_USER_BY_EMAIL = "select role_name, user_id, login, password, firstname, lastname," +
            " email, birth from users join roles on users.role_id = roles.role_id and email = ?";
    private static final String CREATE_USER = "insert into users(login, password, firstname, lastname, email, birth)" +
            "values (?, ?, ?, ?, ?, ?)";
    private static final String CREATE_ACCOUNT = "insert into accounts(user_id) values (?)";
    private static final String FIND_USER_BY_LOGIN_PASSWORD = "select role_name, user_id, login, password, firstname," +
            " lastname, email, birth from users join roles on users.role_id = roles.role_id and login = ? and password = ?";

    @Override
    public void create(User user) throws DaoException {
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(CREATE_USER);
            preparedStatement.setString(PARAM_INDEX_1, user.getLogin());
            preparedStatement.setString(PARAM_INDEX_2, user.getPassword());
            preparedStatement.setString(PARAM_INDEX_3, user.getFirstName());
            preparedStatement.setString(PARAM_INDEX_4, user.getLastName());
            preparedStatement.setString(PARAM_INDEX_5, user.getEmail());
            preparedStatement.setObject(PARAM_INDEX_6, user.getBirth());
            preparedStatement.executeUpdate();
            createAccount(user.getLogin(), connection);
            connection.commit();
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
            try {
                connection.rollback();
            } catch (SQLException sqlException) {
                daoException.addSuppressed(sqlException);
            }
        } finally {
            try {
                closeResource(preparedStatement, daoException);
            } finally {
                pool.releaseConnection(connection);
            }
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            return findByUniqueField(login, connection, FIND_USER_BY_LOGIN);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public Optional<User> findById(Long id) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            return findByUniqueField(id, connection, FIND_USER_BY_ID);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            return findByUniqueField(email, connection, FIND_USER_BY_EMAIL);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        Connection connection = pool.getConnection();
        try {
            return findAll(connection);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public Optional<User> findByLoginPassword(String login, String password) throws DaoException {
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        Optional<User> user = Optional.empty();
        try {
            preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN_PASSWORD);
            preparedStatement.setString(PARAM_INDEX_1, login);
            preparedStatement.setString(PARAM_INDEX_2, password);
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
                try {
                    closeResource(preparedStatement, daoException);
                } finally {
                    pool.releaseConnection(connection);
                }
            }
        }
        return user;
    }

    private void createAccount(String login, Connection connection) throws DaoException {
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(CREATE_ACCOUNT);
            User user = findByUniqueField(login, connection, FIND_USER_BY_LOGIN).orElseThrow(DaoException::new);
            preparedStatement.setLong(PARAM_INDEX_1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            daoException = new DaoException("", e);
        } finally {
            closeResource(preparedStatement, daoException);
        }
    }

    private Optional<User> findByUniqueField(Object fieldValue, Connection connection, String dbQuery) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        Optional<User> user = Optional.empty();
        try {
            preparedStatement = connection.prepareStatement(dbQuery);
            preparedStatement.setObject(PARAM_INDEX_1, fieldValue);
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
        Statement statement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        List<User> users = new ArrayList<>();
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

    private List<Account> findAccountsByUserId(Long userId, Connection connection) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        List<Account> accounts = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(FIND_ACCOUNTS_BY_USER_ID);
            preparedStatement.setLong(PARAM_INDEX_1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                Long accountId = resultSet.getLong(Const.AccountField.ID);
                account.setCards(findCardsByAccountId(accountId, connection));
                account.setId(accountId);
                account.setBalance(new BigDecimal(resultSet.getLong(Const.AccountField.BALANCE)));
                account.setActive(resultSet.getBoolean(Const.AccountField.IS_ACTIVE));
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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        List<Card> cards = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(FIND_CARDS_BY_ACCOUNT_ID);
            preparedStatement.setLong(PARAM_INDEX_1, accountId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Card card = new Card();
                Long cardId = resultSet.getLong(Const.CardField.ID);
                card.setPayments(findPaymentsByCardId(cardId, connection));
                card.setId(cardId);
                card.setCardNumber(resultSet.getString(Const.CardField.NUMBER));
                card.setValidThruDate(LocalDate.parse(resultSet.getString(Const.CardField.VALID_THRU)));
                card.setCvc(resultSet.getString(Const.CardField.CVC));
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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        List<Payment> payments = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(FIND_PAYMENTS_BY_CARD_ID);
            preparedStatement.setLong(PARAM_INDEX_1, cardId);
            preparedStatement.setLong(PARAM_INDEX_2, cardId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment();
                payment.setId(resultSet.getLong(Const.PaymentField.ID));
                payment.setSourceCardInfo(findCardInfoByCardId(
                        resultSet.getLong(Const.PaymentField.SOURCE_CARD_ID), connection));
                payment.setDestinationCardInfo(findCardInfoByCardId(
                        resultSet.getLong(Const.PaymentField.DESTINATION_CARD_ID), connection));
                payment.setAmount(new BigDecimal(resultSet.getLong(Const.PaymentField.AMOUNT)));
                payment.setPaymentInstant(resultSet.getTimestamp(Const.PaymentField.INSTANT).toLocalDateTime());
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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        CardInfo cardInfo = new CardInfo();
        try {
            preparedStatement = connection.prepareStatement(FIND_CARD_BY_CARD_ID);
            preparedStatement.setLong(PARAM_INDEX_1, cardId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cardInfo.setId(cardId);
                cardInfo.setCardNumber(resultSet.getString(Const.CardField.NUMBER));
                cardInfo.setValidThruDate(LocalDate.parse(resultSet.getString(Const.CardField.VALID_THRU)));
                cardInfo.setCvc(resultSet.getString(Const.CardField.CVC));
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
        Long userId = resultSet.getLong(Const.UserField.ID);
        List<Account> userAccounts = findAccountsByUserId(userId, connection);
        user.setAccounts(userAccounts);
        user.setId(userId);
        user.setRoleName(resultSet.getString(Const.UserField.ROLE_NAME));
        user.setLogin(resultSet.getString(Const.UserField.LOGIN));
        user.setFirstName(resultSet.getString(Const.UserField.FIRST_NAME));
        user.setLastName(resultSet.getString(Const.UserField.LAST_NAME));
        user.setEmail(resultSet.getString(Const.UserField.EMAIL));
        user.setBirth(LocalDate.parse(resultSet.getString(Const.UserField.BIRTH)));
        return user;
    }
}
