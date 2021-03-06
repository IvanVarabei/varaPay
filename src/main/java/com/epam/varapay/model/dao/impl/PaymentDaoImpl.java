package com.epam.varapay.model.dao.impl;

import com.epam.varapay.model.dao.ColumnLabel;
import com.epam.varapay.model.dao.GenericDao;
import com.epam.varapay.model.dao.PaymentDao;
import com.epam.varapay.model.dao.builder.impl.CardBuilder;
import com.epam.varapay.model.entity.Card;
import com.epam.varapay.model.entity.Payment;
import com.epam.varapay.exception.DaoException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoImpl extends GenericDao<Card> implements PaymentDao {
    private static final String SET_PAYMENT = "insert into payments (source_card_id, destination_card_id, amount) " +
            "values(?, ?, ?)";
    private static final String FIND_CARD_ID_BY_NUMBER = "select card_id from cards where card_number = ?";
    private static final String FIND_ACCOUNT_ID_BY_CARD_ID = "select account_id from cards where card_id = ?";
    private static final String ADD_ACCOUNT_BALANCE = "update accounts set balance = balance + ? where account_id= ?";
    private static final String FIND_CARD_BY_ID = "select card_id, card_number, valid_thru, cvc, cards.account_id,\n" +
            "       balance, is_active, users.user_id, users.login, password, salt,users.email,\n" +
            "       users.firstname, users.lastname, users.birth, roles.role_name from cards\n" +
            "    join accounts on card_id = ? and cards.account_id = accounts.account_id\n" +
            "    join users on accounts.user_id = users.user_id\n" +
            "    join roles on users.role_id = roles.role_id";
    private static final String FIND_PAYMENTS_BY_CARD_ID = "select payment_id, amount, source_card_id, " +
            "destination_card_id, payment_instant from payments where source_card_id = ? or destination_card_id = ? " +
            "order by payment_instant desc limit ? offset ?";
    private static final String FIND_NUMBER_OF_RECORDS = "select count(*) from payments where source_card_id = ?" +
            " or destination_card_id = ?;";

    public PaymentDaoImpl() {
        super(new CardBuilder());
    }

    @Override
    public List<Payment> findPaymentsByCardId(Long cardId, int limit, int offset) throws DaoException {
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Payment> payments = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(FIND_PAYMENTS_BY_CARD_ID);
            setParameters(preparedStatement, cardId, cardId, limit, offset);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                payments.add(instantiatePayment(resultSet, connection));
            }
        } catch (SQLException e) {
            throw new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(resultSet);
            } finally {
                try {
                    closeResource(preparedStatement);
                } finally {
                    pool.releaseConnection(connection);
                }
            }
        }
        return payments;
    }

    @Override
    public Long findAmountOfRecordsByCardId(Long cardId) throws DaoException {
        return findLong(FIND_NUMBER_OF_RECORDS, ColumnLabel.COUNT, cardId, cardId).orElseThrow(DaoException::new);
    }

    @Override
    public void makePayment(Long sourceCardId, String destinationCardNumber, Long amount) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            startSerializableTransaction(connection);
            Long destinationCardId = findLong(FIND_CARD_ID_BY_NUMBER, connection,
                    ColumnLabel.CARD_ID, destinationCardNumber).orElseThrow(DaoException::new);
            Long sourceAccountId = findLong(FIND_ACCOUNT_ID_BY_CARD_ID, connection,
                    ColumnLabel.ACCOUNT_ID, sourceCardId).orElseThrow(DaoException::new);
            Long destAccountId = findLong(FIND_ACCOUNT_ID_BY_CARD_ID, connection,
                    ColumnLabel.ACCOUNT_ID, destinationCardId).orElseThrow(DaoException::new);
            executeUpdate(ADD_ACCOUNT_BALANCE, connection, -amount, sourceAccountId);
            executeUpdate(ADD_ACCOUNT_BALANCE, connection, amount, destAccountId);
            executeUpdate(SET_PAYMENT, connection, sourceCardId, destinationCardId, amount);
            endTransaction(connection);
        } catch (SQLException | DaoException e) {
            cancelTransaction(connection);
            throw e instanceof DaoException ? (DaoException) e : new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    private Payment instantiatePayment(ResultSet resultSet, Connection connection) throws SQLException, DaoException {
        Payment payment = new Payment();
        Long sourceCardId = resultSet.getLong(ColumnLabel.SOURCE_CARD_ID);
        Long destinationCardId = resultSet.getLong(ColumnLabel.DESTINATION_CARD_ID);
        Card sourceCard = executeForSingleResult(FIND_CARD_BY_ID, connection, sourceCardId)
                .orElseThrow(DaoException::new);
        Card destinationCard = executeForSingleResult(FIND_CARD_BY_ID, connection, destinationCardId)
                .orElseThrow(DaoException::new);
        payment.setSourceCard(sourceCard);
        payment.setDestinationCard(destinationCard);
        payment.setId(resultSet.getLong(ColumnLabel.PAYMENT_ID));
        payment.setAmount(BigDecimal.valueOf(resultSet.getLong(ColumnLabel.PAYMENT_AMOUNT)).movePointLeft(2));
        payment.setPaymentInstant(resultSet.getTimestamp(ColumnLabel.INSTANT).toLocalDateTime());
        return payment;
    }
}
