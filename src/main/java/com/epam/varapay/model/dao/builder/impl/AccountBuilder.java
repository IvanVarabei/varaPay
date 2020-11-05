package com.epam.varapay.model.dao.builder.impl;

import com.epam.varapay.model.entity.Account;
import com.epam.varapay.model.entity.User;
import com.epam.varapay.model.dao.ColumnLabel;
import com.epam.varapay.model.dao.builder.IdentifiableBuilder;

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
