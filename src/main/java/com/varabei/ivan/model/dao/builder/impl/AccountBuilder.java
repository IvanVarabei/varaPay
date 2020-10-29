package com.varabei.ivan.model.dao.builder.impl;

import com.varabei.ivan.model.dao.ColumnLabel;
import com.varabei.ivan.model.dao.builder.IdentifiableBuilder;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.User;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountBuilder implements IdentifiableBuilder<Account> {
    private final IdentifiableBuilder<User> userBuilder = new UserBuilder();
    private static final int CENT_TO_DOLLAR = 2;

    @Override
    public Account build(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        User user = userBuilder.build(resultSet);
        account.setUser(user);
        account.setId(resultSet.getLong(ColumnLabel.ACCOUNT_ID));
        account.setBalance(BigDecimal.valueOf(resultSet.getLong(ColumnLabel.BALANCE)).movePointLeft(CENT_TO_DOLLAR));
        account.setActive(resultSet.getBoolean(ColumnLabel.IS_ACTIVE));
        return account;
    }
}
