package com.varabei.ivan.model.dao.builder.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.model.dao.builder.IdentifiableBuilder;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.Bid;

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
        bid.setId(resultSet.getLong(Const.BidField.ID));
        bid.setState(resultSet.getString(Const.BidField.STATE));
        bid.setTopUp(resultSet.getBoolean(Const.BidField.IS_TOP_UP));
        bid.setAmount(BigDecimal.valueOf(resultSet.getLong(Const.BidField.AMOUNT)).movePointLeft(2));
        bid.setPlacingDateTime((resultSet.getTimestamp(Const.BidField.PLACING_DATE_TIME).toLocalDateTime()));
        bid.setClientMessage(resultSet.getString(Const.BidField.CLIENT_MESSAGE));
        bid.setAdminComment(resultSet.getString(Const.BidField.ADMIN_COMMENT));
        return bid;
    }
}
