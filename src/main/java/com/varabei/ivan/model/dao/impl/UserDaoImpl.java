package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.ColumnLabel;
import com.varabei.ivan.model.dao.GenericDao;
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
    private static final String FIND_PASSWORD_BY_ID = "select password from users where user_id = ?";
    private static final String FIND_PASSWORD_BY_LOGIN = "select password from users where login = ?";
    private static final String FIND_SALT_BY_ID = "select salt from users where user_id = ?";
    private static final String FIND_SALT_BY_LOGIN_OR_EMAIL = "select salt from users where login = ? or email = ?";

    private static final String CREATE_USER = "insert into users(login, password, salt, firstname, lastname, email, birth, secret_word)" +
            "values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PASSWORD_BY_EMAIL = "update users\n" +
            "set password = ?, salt = ? where email = ?";
    private static final String UPDATE_PASSWORD_BY_ID = "update users\n" +
            "set password = ?, salt = ? where user_id = ?";
    private static final String IF_PRESENT_BY_ID_AND_PASSWORD = "select exists(select 1 from users where user_id = ? and password = ?)";
    private static final String IF_PRESENT_BY_ACCOUNT_ID_AND_SECRET_WORD = "select exists(select 1 from users " +
            "join accounts on account_id = ? and secret_word = ?)";

    public UserDaoImpl() {
        super(new UserBuilder());
    }

    @Override
    public void create(User user, String hashedPassword, String salt, String secretWord) throws DaoException {
        executeUpdate(CREATE_USER, user.getLogin(), hashedPassword, salt, user.getFirstName(),
                user.getLastName(), user.getEmail(), user.getBirth(), secretWord);
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
    public Optional<String> findPasswordByLogin(String login) throws DaoException {
        return findString(FIND_PASSWORD_BY_LOGIN, ColumnLabel.PASSWORD, login);
    }

    @Override
    public Optional<String> findSaltByLoginOrEmail(String loginOrEmail) throws DaoException {
        return findString(FIND_SALT_BY_LOGIN_OR_EMAIL, ColumnLabel.SALT, loginOrEmail, loginOrEmail);
    }

    @Override
    public List<User> findAll() throws DaoException {
        return executeQuery(FIND_ALL_USERS);
    }

    @Override
    public void updatePassword(String email, String newPassword, String newSalt) throws DaoException {
        executeUpdate(UPDATE_PASSWORD_BY_EMAIL, newPassword, newSalt, email);
    }

    @Override
    public boolean isAuthenticSecretWord(Long accountId, String secretWord) throws DaoException {
        return findBoolean(IF_PRESENT_BY_ACCOUNT_ID_AND_SECRET_WORD, ColumnLabel.EXISTS, accountId, secretWord);
    }
}
