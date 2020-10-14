package com.varabei.ivan.model.dao.builder.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.model.dao.builder.IdentifiableBuilder;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.User;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountBuilder implements IdentifiableBuilder<Account> {
    private final IdentifiableBuilder<User> userBuilder = new UserBuilder();

    @Override
    public Account build(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        User user = userBuilder.build(resultSet);
        account.setUser(user);
        account.setId(resultSet.getLong(Const.AccountField.ID));
        account.setBalance(new BigDecimal(resultSet.getLong(Const.AccountField.BALANCE)));
        account.setActive(resultSet.getBoolean(Const.AccountField.IS_ACTIVE));
        return account;
    }
}
