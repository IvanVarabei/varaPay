package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.CardDao;
import com.varabei.ivan.model.dao.GenericDao;
import com.varabei.ivan.model.dao.builder.impl.CardBuilder;
import com.varabei.ivan.model.entity.Card;
import com.varabei.ivan.model.entity.name.CardField;
import com.varabei.ivan.model.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CardDaoImpl extends GenericDao<Card> implements CardDao {
    private static final String FIND_CARDS_BY_ACCOUNT_ID =
            "select card_id, card_number, valid_thru, cvc, cards.account_id,\n" +
                    "       balance, is_active, users.user_id, users.login, password, salt,users.email,\n" +
                    "       users.firstname, users.lastname, users.birth, roles.role_name from cards\n" +
                    "    join accounts on cards.account_id = ? and cards.account_id = accounts.account_id\n" +
                    "    and cards.is_abandoned = false\n" +
                    "    join users on accounts.user_id = users.user_id\n" +
                    "    join roles on users.role_id = roles.role_id";
    private static final String FIND_CARD_BY_ID = "select card_id, card_number, valid_thru, cvc, cards.account_id,\n" +
            "       balance, is_active, users.user_id, users.login, password, salt,users.email,\n" +
            "       users.firstname, users.lastname, users.birth, roles.role_name from cards\n" +
            "    join accounts on card_id = ? and cards.account_id = accounts.account_id\n" +
            "    join users on accounts.user_id = users.user_id\n" +
            "    join roles on users.role_id = roles.role_id";
    private static final String FIND_CARD_BY_CARD_NUMBER = "select card_id, card_number, valid_thru, cvc, cards.account_id,\n" +
            "       balance, is_active, users.user_id, users.login, password, salt,users.email,\n" +
            "       users.firstname, users.lastname, users.birth, roles.role_name from cards\n" +
            "    join accounts on card_number = ? and cards.account_id = accounts.account_id\n" +
            "    join users on accounts.user_id = users.user_id\n" +
            "    join roles on users.role_id = roles.role_id";
    private static final String ABANDON_CARD = "update cards set is_abandoned = true where card_Id = ?";
    private static final String CREATE_CARD = "insert into cards (account_id) values (?)";
    private static final String FIND_CVC_OF_THE_LAST_CREATED_CARD = "select cvc from cards where account_id = ?" +
            " order by card_id desc limit 1";

    public CardDaoImpl() {
        super(new CardBuilder());
    }

    @Override
    public String createCardAndReturnCvc(Long accountId) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            startTransaction(connection);
            executeUpdate(CREATE_CARD, connection, accountId);
            String cvcOfTheLastCreatedCard = findString(FIND_CVC_OF_THE_LAST_CREATED_CARD, connection,
                    CardField.CVC, accountId).orElseThrow(DaoException::new);
            endTransaction(connection);
            return cvcOfTheLastCreatedCard;
        } catch (SQLException | DaoException e) {
            DaoException daoException = e instanceof DaoException ? (DaoException) e : new DaoException(e);
            cancelTransaction(connection);
            throw daoException;
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public Optional<Card> findById(Long id) throws DaoException {
        return executeForSingleResult(FIND_CARD_BY_ID, id);
    }

    @Override
    public Optional<Card> findByCardNumber(String cardNumber) throws DaoException {
        return executeForSingleResult(FIND_CARD_BY_CARD_NUMBER, cardNumber);
    }

    @Override
    public List<Card> findByAccountId(Long accountId) throws DaoException {
        return executeQuery(FIND_CARDS_BY_ACCOUNT_ID, accountId);
    }

    @Override
    public void delete(Long cardId) throws DaoException {
        executeUpdate(ABANDON_CARD, cardId);
    }
}
