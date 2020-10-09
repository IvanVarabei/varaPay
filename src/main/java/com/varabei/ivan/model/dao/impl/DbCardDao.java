package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.model.dao.CardDao;
import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.entity.AccountInfo;
import com.varabei.ivan.model.entity.Card;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class DbCardDao extends GenericDao implements CardDao {
    private static final String FIND_CARD_BY_ID = "select cardnumber, validthru, cvc, accounts.accountid, accounts.isactive, accounts.balance  from cards\n" +
            "join accounts on cards.accountid = accounts.accountid and cards.cardid = ?";
    @Override
    public Optional<Card> findById(Long id) throws DaoException {
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        Optional<Card> card = Optional.empty();
        try {
            preparedStatement = connection.prepareStatement(FIND_CARD_BY_ID);
            preparedStatement.setLong(PARAM_INDEX_1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                card = Optional.of(instantiateCard(resultSet));
                card.ifPresent(c -> c.setId(id));
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
        return card;
    }

    private Card instantiateCard(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        AccountInfo accountInfo = new AccountInfo();
        card.setAccountInfo(accountInfo);
        card.setCardNumber(resultSet.getString(Const.CardField.NUMBER));
        card.setValidThruDate(LocalDate.parse(resultSet.getString(Const.CardField.VALID_THRU)));
        card.setCvc(resultSet.getString(Const.CardField.CVC));
        accountInfo.setId(resultSet.getLong(Const.AccountField.ID));
        accountInfo.setBalance(new BigDecimal(resultSet.getLong(Const.AccountField.BALANCE)));
        accountInfo.setActive(resultSet.getBoolean(Const.AccountField.IS_ACTIVE));
        return card;
    }
}
