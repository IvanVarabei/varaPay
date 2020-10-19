package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.dao.builder.impl.UserBuilder;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends GenericDao<User> implements UserDao {
    private static final String FIND_ALL_USERS = "select role_name, user_id, login, password, salt," +
            " firstname, lastname, email, birth from users join roles on users.role_id = roles.role_id";
    private static final String FIND_USER_BY_ID = "select role_name, user_id, login, password, salt, firstname, lastname," +
            " email, birth from users join roles on users.role_id = roles.role_id and user_id = ?";
    private static final String FIND_USER_BY_LOGIN = "select role_name, user_id, login, password, salt, firstname, lastname," +
            " email, birth from users join roles on users.role_id = roles.role_id and login = ?";
    private static final String FIND_USER_BY_EMAIL = "select role_name, user_id, login, password, salt, firstname, lastname," +
            " email, birth from users join roles on users.role_id = roles.role_id and email = ?";
    private static final String CREATE_USER = "insert into users(login, password, salt, firstname, lastname, email, birth)" +
            "values (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_USER_BY_LOGIN_PASSWORD = "select role_name, user_id, login, password, salt, firstname," +
            " lastname, email, birth from users join roles on users.role_id = roles.role_id and login = ? and password = ?";
    private static final String UPDATE_PASSWORD_BY_EMAIL = "update users\n" +
            "set password = ?, salt = ? where email = ?";
    private static final String UPDATE_PASSWORD_BY_ID = "update users\n" +
            "set password = ?, salt = ? where user_id = ?";
    private static final String IF_PRESENT_BY_ID_AND_PASSWORD = "select exists(select 1 from users where user_id = ? and password = ?)";
    private static final String RESULT_SET_COLUMN_LABEL_EXISTS = "exists";

    public UserDaoImpl() {
        super(new UserBuilder());
    }

    @Override
    public void create(User user) throws DaoException {
        executeUpdate(CREATE_USER, user.getLogin(), user.getPassword(), user.getSalt(), user.getFirstName(),
                user.getLastName(), user.getEmail(), user.getBirth());
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        return executeForSingleResult(FIND_USER_BY_LOGIN, login);
    }

    @Override
    public Optional<User> findById(Long id) throws DaoException {
        return executeForSingleResult(FIND_USER_BY_ID, id);
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        return executeForSingleResult(FIND_USER_BY_EMAIL, email);
    }

    @Override
    public List<User> findAll() throws DaoException {
        return executeQuery(FIND_ALL_USERS);
    }

    @Override
    public Optional<User> findByLoginPassword(String login, String password) throws DaoException {
        return executeForSingleResult(FIND_USER_BY_LOGIN_PASSWORD, login, password);
    }

    @Override
    public void updatePassword(String email, String newPassword, String newSalt) throws DaoException {
        executeUpdate(UPDATE_PASSWORD_BY_EMAIL, newPassword, newSalt, email);
    }

    @Override
    public void updatePassword(Long id, String newPassword, String newSalt) throws DaoException {
        executeUpdate(UPDATE_PASSWORD_BY_ID, newPassword, newSalt, id);
    }

    @Override
    public boolean checkPresenceByIdPassword(Long id, String password) throws DaoException {
        return findBoolean(IF_PRESENT_BY_ID_AND_PASSWORD, RESULT_SET_COLUMN_LABEL_EXISTS, id, password);
    }
}
