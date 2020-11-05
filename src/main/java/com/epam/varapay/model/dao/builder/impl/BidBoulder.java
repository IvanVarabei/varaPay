package com.epam.varapay.model.dao.builder.impl;

import com.epam.varapay.model.entity.Account;
import com.epam.varapay.model.entity.Bid;
import com.epam.varapay.model.entity.BidState;
import com.epam.varapay.model.entity.CustomCurrency;
import com.epam.varapay.model.dao.ColumnLabel;
import com.epam.varapay.model.dao.builder.IdentifiableBuilder;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BidBoulder implements IdentifiableBuilder<Bid> {
    private final IdentifiableBuilder<Account> accountBuilder = new AccountBuilder();

    @Override
    public Bid build(ResultSet resultSet) throws SQLException {
        Bid bid = new Bid();
        Account account = accountBuilder.build(resultSet);
        bid.setAccount(account);
        bid.setId(resultSet.getLong(ColumnLabel.BID_ID));
        bid.setState(BidState.valueOf(resultSet.getString(ColumnLabel.STATE).toUpperCase()));
        bid.setTopUp(resultSet.getBoolean(ColumnLabel.IS_TOP_UP));
        bid.setAmount(BigDecimal.valueOf(resultSet.getLong(ColumnLabel.BID_AMOUNT)).movePointLeft(2));
        bid.setAmountInChosenCurrency(BigDecimal.valueOf(resultSet.getDouble(ColumnLabel.AMOUNT_IN_CHOSEN_CURRENCY)));
        bid.setCurrency(CustomCurrency.valueOf(resultSet.getString(ColumnLabel.CURRENCY_NAME)));
        bid.setPlacingDateTime((resultSet.getTimestamp(ColumnLabel.PLACING_DATE_TIME).toLocalDateTime()));
        bid.setClientMessage(resultSet.getString(ColumnLabel.CLIENT_MESSAGE));
        bid.setAdminComment(resultSet.getString(ColumnLabel.ADMIN_COMMENT));
        return bid;
    }
}
