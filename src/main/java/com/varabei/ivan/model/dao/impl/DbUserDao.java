package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.DaoException;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.dao.pool.ConnectionPool;
import com.varabei.ivan.model.entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DbUserDao implements UserDao {
    private static final int PREPARED_STATEMENT_FIRST_INDEX = 1;
    private static final int PREPARED_STATEMENT_SECOND_INDEX = 2;
    private static final int PREPARED_STATEMENT_THIRD_INDEX = 3;
    private static final int PREPARED_STATEMENT_FOURTH_INDEX = 4;
    private static final String SQL_CREATE_BOOK = "insert into book(name, numberofpage, author) values(?,?,?)";
    private static final String SQL_READ_BOOK_BY_ID = "select name, numberofpage, author from book where bookid = ?";
    private static final String FIND_ALL = "select userid, roleid, login, password, firstname, lastname, email, birth from users";
    private static final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        Connection connection = pool.getConnection();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setFirstName(resultSet.getString("firstname"));
                user.setLastName(resultSet.getString("lastname"));
                user.setEmail(resultSet.getString("email"));
                user.setBirth(LocalDate.parse(resultSet.getString("birth")));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException("can not get access to db", e);
        } finally {
            pool.releaseConnection(connection);
        }
        return users;
    }
}
