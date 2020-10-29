package com.varabei.ivan.model.dao.builder.impl;

import com.varabei.ivan.model.dao.ColumnLabel;
import com.varabei.ivan.model.dao.builder.IdentifiableBuilder;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.Card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CardBuilder implements IdentifiableBuilder<Card> {
    private final IdentifiableBuilder<Account> accountBuilder = new AccountBuilder();

    @Override
    public Card build(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        Account account = accountBuilder.build(resultSet);
        card.setAccount(account);
        card.setId(resultSet.getLong(ColumnLabel.CARD_ID));
        card.setCardNumber(resultSet.getString(ColumnLabel.CARD_NUMBER));
        card.setValidThru(LocalDate.parse(resultSet.getString(ColumnLabel.VALID_THRU)));
        card.setCvc(resultSet.getString(ColumnLabel.CVC));
        return card;
    }
}
