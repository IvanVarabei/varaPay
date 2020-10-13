package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.model.dao.CardDao;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.entity.AccountInfo;
import com.varabei.ivan.model.entity.Card;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbCardDao extends GenericDao implements CardDao {
    private static final String FIND_CARD_BY_ID = "select card_number, valid_thru, cvc, accounts.account_id," +
            " accounts.is_active, accounts.balance  from cards join accounts " +
            "on cards.account_id = accounts.account_id and cards.card_id = ?";
    private static final String ABANDON_CARD = "update cards set is_abandoned = true where card_Id = ?";
    private static final String CREATE_CARD = "insert into cards (account_id) values (?)";

//    public static void main(String[] args) {
//        try {
//            URL queryUrl = new URL("https://api.livecoin.net/exchange/order_book?currencyPair=BTC/USD");
//            HttpURLConnection connection = (HttpURLConnection) queryUrl.openConnection();
//            connection.setDoOutput(true);
//
//            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = rd.readLine()) != null) {
//                sb.append(line + '\n');
//            }
//            System.out.println("before");
//            Double d = parseBitcoinCost(sb.toString());
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    static double parseBitcoinCost(String s) {
//        Pattern pattern = Pattern.compile("(?<=\"asks\":\\[\\[\").+?(?=\")");
//        Matcher matcher = pattern.matcher(s);
//        matcher.find();
//        return Double.parseDouble(matcher.group());
//    }

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

    @Override
    public void create(Long accountId) throws DaoException {
        //executeQueryAcceptingOneValue(accountId, CREATE_CARD);
    }

    @Override
    public void delete(Long cardId) throws DaoException {
        //executeQueryAcceptingOneValue(cardId, ABANDON_CARD);
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
