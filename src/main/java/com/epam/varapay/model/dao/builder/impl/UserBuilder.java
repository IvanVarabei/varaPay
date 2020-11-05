package com.epam.varapay.model.dao.builder.impl;

import com.epam.varapay.model.entity.Role;
import com.epam.varapay.model.entity.User;
import com.epam.varapay.model.dao.ColumnLabel;
import com.epam.varapay.model.dao.builder.IdentifiableBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserBuilder implements IdentifiableBuilder<User> {
    @Override
    public User build(ResultSet resultSet) throws SQLException {
        User user = new User();
        Long userId = resultSet.getLong(ColumnLabel.USER_ID);
        user.setId(userId);
        user.setRole(Role.valueOf(resultSet.getString(ColumnLabel.ROLE_NAME).toUpperCase()));
        user.setLogin(resultSet.getString(ColumnLabel.LOGIN));
        user.setFirstName(resultSet.getString(ColumnLabel.FIRST_NAME));
        user.setLastName(resultSet.getString(ColumnLabel.LAST_NAME));
        user.setEmail(resultSet.getString(ColumnLabel.EMAIL));
        user.setBirth(LocalDate.parse(resultSet.getString(ColumnLabel.BIRTH)));
        return user;
    }
}
