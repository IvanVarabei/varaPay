package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.model.entity.Payment;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.dao.PaymentDao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DbPaymentDao extends GenericDao implements PaymentDao {
    private static final String SET_PAYMENT = "insert into payments (source_card_id, destination_card_id, amount) " +
            "values(?, ?, ?)";
    private static final String FIND_CARD_ID_BY_NUMBER = "select card_id from cards where card_number = ?";
    private static final String FIND_ACCOUNT_ID_BY_CARD_ID = "select account_id from cards where card_id = ?";
    private static final String ADD_ACCOUNT_BALANCE = "update accounts set balance = balance + ? where account_id= ?";

    @Override
    public void makePayment(Long sourceCardId, String destinationCardNumber, BigDecimal amount) throws DaoException {
        Connection connection = pool.getConnection();
        DaoException daoException;
        try {
            startTransaction(connection);
            Long destinationCardId = findLong(FIND_CARD_ID_BY_NUMBER, connection,
                    Const.CardField.ID, destinationCardNumber).orElseThrow(DaoException::new);
            Long sourceAccountId = findLong(FIND_ACCOUNT_ID_BY_CARD_ID, connection,
                    Const.AccountField.ID,sourceCardId).orElseThrow(DaoException::new);
            Long destAccountId = findLong(FIND_ACCOUNT_ID_BY_CARD_ID, connection,
                    Const.AccountField.ID, destinationCardId).orElseThrow(DaoException::new);
            executeUpdate(ADD_ACCOUNT_BALANCE, connection, -amount.longValue(), sourceAccountId);
            executeUpdate(ADD_ACCOUNT_BALANCE, connection, amount.longValue(), destAccountId);
            executeUpdate(SET_PAYMENT, connection, sourceCardId, destinationCardId, amount.longValue());
            endTransaction(connection);
        } catch (SQLException | DaoException e) {
            daoException = new DaoException("can not get access to db", e);
            cancelTransaction(connection, daoException);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<Payment> findPaymentsByCardId(Long cardId) throws DaoException {
        return null;
    }

    @Override
    public List<Payment> findOutgoingPayments(Long cardId) throws DaoException {
        return null;
    }

    @Override
    public List<Payment> findIncomingPayments(Long cardId) throws DaoException {
        return null;
    }
}
