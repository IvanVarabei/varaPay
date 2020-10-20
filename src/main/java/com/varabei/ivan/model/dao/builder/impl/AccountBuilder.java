package com.varabei.ivan.model.dao.builder.impl;

import com.varabei.ivan.model.dao.builder.IdentifiableBuilder;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.entity.name.AccountField;

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
        account.setId(resultSet.getLong(AccountField.ID));
        account.setBalance(BigDecimal.valueOf(resultSet.getLong(AccountField.BALANCE)).movePointLeft(2));
        account.setActive(resultSet.getBoolean(AccountField.IS_ACTIVE));
        return account;
    }
}
