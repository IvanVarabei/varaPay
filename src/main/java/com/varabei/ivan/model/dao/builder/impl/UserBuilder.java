package com.varabei.ivan.model.dao.builder.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.model.dao.builder.IdentifiableBuilder;
import com.varabei.ivan.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserBuilder implements IdentifiableBuilder<User> {
    @Override
    public User build(ResultSet resultSet) throws SQLException {
        User user = new User();
        Long userId = resultSet.getLong(Const.UserField.ID);
        user.setId(userId);
        user.setRoleName(resultSet.getString(Const.UserField.ROLE_NAME));
        user.setLogin(resultSet.getString(Const.UserField.LOGIN));
        user.setFirstName(resultSet.getString(Const.UserField.FIRST_NAME));
        user.setLastName(resultSet.getString(Const.UserField.LAST_NAME));
        user.setEmail(resultSet.getString(Const.UserField.EMAIL));
        user.setBirth(LocalDate.parse(resultSet.getString(Const.UserField.BIRTH)));
        return user;
    }
}
