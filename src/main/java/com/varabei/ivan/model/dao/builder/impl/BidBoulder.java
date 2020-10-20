package com.varabei.ivan.model.dao.builder.impl;

import com.varabei.ivan.model.dao.builder.IdentifiableBuilder;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.entity.name.BidField;

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
        bid.setId(resultSet.getLong(BidField.ID));
        bid.setState(resultSet.getString(BidField.STATE));
        bid.setTopUp(resultSet.getBoolean(BidField.IS_TOP_UP));
        bid.setAmount(BigDecimal.valueOf(resultSet.getLong(BidField.AMOUNT)).movePointLeft(2));
        bid.setPlacingDateTime((resultSet.getTimestamp(BidField.PLACING_DATE_TIME).toLocalDateTime()));
        bid.setClientMessage(resultSet.getString(BidField.CLIENT_MESSAGE));
        bid.setAdminComment(resultSet.getString(BidField.ADMIN_COMMENT));
        return bid;
    }
}
