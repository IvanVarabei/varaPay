package com.varabei.ivan.model.dao.builder.impl;

import com.varabei.ivan.model.dao.builder.IdentifiableBuilder;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.entity.name.UserField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserBuilder implements IdentifiableBuilder<User> {
    @Override
    public User build(ResultSet resultSet) throws SQLException {
        User user = new User();
        Long userId = resultSet.getLong(UserField.ID);
        user.setId(userId);
        user.setRoleName(resultSet.getString(UserField.ROLE_NAME));
        user.setLogin(resultSet.getString(UserField.LOGIN));
        user.setFirstName(resultSet.getString(UserField.FIRST_NAME));
        user.setLastName(resultSet.getString(UserField.LAST_NAME));
        user.setEmail(resultSet.getString(UserField.EMAIL));
        user.setBirth(LocalDate.parse(resultSet.getString(UserField.BIRTH)));
        return user;
    }
}
