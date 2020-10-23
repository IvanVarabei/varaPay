package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.GenericDao;
import com.varabei.ivan.model.dao.PaymentDao;
import com.varabei.ivan.model.dao.builder.impl.CardBuilder;
import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.entity.name.AccountField;
import com.varabei.ivan.model.entity.name.CardField;
import com.varabei.ivan.model.entity.name.PaymentField;
import com.varabei.ivan.model.exception.DaoException;

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
    private static final String FIND_NUMBER_OF_RECORDS="select count(*) from payments where source_card_id = ?" +
            " or destination_card_id = ?;";
    private static final String FIND_OUTGOING_PAYMENTS_BY_CARD_ID = "select payment_id, amount, source_card_id," +
            " destination_card_id, payment_instant from payments where source_card_id = ?";
    private static final String FIND_INCOMING_PAYMENTS_BY_CARD_ID = "select payment_id, amount, source_card_id," +
            " destination_card_id, payment_instant from payments where destination_card_id = ?";
    private static final String RESULT_SET_COLUMN_LABEL_COUNT = "count";

    public PaymentDaoImpl() {
        super(new CardBuilder());
    }

    @Override
    public Long findNumberOfRecordsByCardId(Long cardId) throws DaoException {
        return findLong(FIND_NUMBER_OF_RECORDS, RESULT_SET_COLUMN_LABEL_COUNT,
                cardId, cardId).orElseThrow(DaoException::new);
    }

    @Override
    public void makePayment(Long sourceCardId, String destinationCardNumber, Long amount) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            startTransaction(connection);
            Long destinationCardId = findLong(FIND_CARD_ID_BY_NUMBER, connection,
                    CardField.ID, destinationCardNumber).orElseThrow(DaoException::new);
            Long sourceAccountId = findLong(FIND_ACCOUNT_ID_BY_CARD_ID, connection,
                    AccountField.ID, sourceCardId).orElseThrow(DaoException::new);
            Long destAccountId = findLong(FIND_ACCOUNT_ID_BY_CARD_ID, connection,
                    AccountField.ID, destinationCardId).orElseThrow(DaoException::new);
            executeUpdate(ADD_ACCOUNT_BALANCE, connection, -amount, sourceAccountId);
            executeUpdate(ADD_ACCOUNT_BALANCE, connection, amount, destAccountId);
            executeUpdate(SET_PAYMENT, connection, sourceCardId, destinationCardId, amount);
            endTransaction(connection);
        } catch (SQLException | DaoException e) {
            DaoException daoException = e instanceof DaoException ? (DaoException) e : new DaoException(e);
            cancelTransaction(connection);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<Payment> findPaymentsByCardId(Long cardId, int limit, int offset) throws DaoException {
        return findPayments(FIND_PAYMENTS_BY_CARD_ID, cardId, limit, offset);
    }

    @Override
    public List<Payment> findOutgoingPayments(Long cardId) throws DaoException {
        return null;//findPayments(FIND_OUTGOING_PAYMENTS_BY_CARD_ID, cardId);
    }

    @Override
    public List<Payment> findIncomingPayments(Long cardId) throws DaoException {
        return null;// findPayments(FIND_INCOMING_PAYMENTS_BY_CARD_ID, cardId);
    }

    private List<Payment> findPayments(String query, Long cardId, int limit, int offset) throws DaoException {
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Payment> payments = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(query);
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

    private Payment instantiatePayment(ResultSet resultSet, Connection connection) throws SQLException, DaoException {
        Payment payment = new Payment();
        Long sourceCardId = resultSet.getLong(PaymentField.SOURCE_CARD_ID);
        Long destinationCardId = resultSet.getLong(PaymentField.DESTINATION_CARD_ID);
        Card sourceCard = executeForSingleResult(FIND_CARD_BY_ID, connection, sourceCardId)
                .orElseThrow(DaoException::new);
        Card destinationCard = executeForSingleResult(FIND_CARD_BY_ID, connection, destinationCardId)
                .orElseThrow(DaoException::new);
        payment.setSourceCard(sourceCard);
        payment.setDestinationCard(destinationCard);
        payment.setId(resultSet.getLong(PaymentField.ID));
        payment.setAmount(BigDecimal.valueOf(resultSet.getLong(PaymentField.AMOUNT)).movePointLeft(2));
        payment.setPaymentInstant(resultSet.getTimestamp(PaymentField.INSTANT).toLocalDateTime());
        return payment;
    }
}
