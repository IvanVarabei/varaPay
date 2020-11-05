package com.epam.varapay.model.dao.impl;

import com.epam.varapay.model.dao.UserDao;
import com.epam.varapay.model.dao.ColumnLabel;
import com.epam.varapay.model.dao.GenericDao;
import com.epam.varapay.model.dao.builder.impl.UserBuilder;
import com.epam.varapay.model.entity.User;
import com.epam.varapay.model.exception.DaoException;

import java.util.Optional;

public class UserDaoImpl extends GenericDao<User> implements UserDao {
    private static final String FIND_USER_BY_ID = "select role_name, user_id, login, password, salt, firstname, " +
            "lastname, email, birth from users join roles on users.role_id = roles.role_id and user_id = ?";
    private static final String FIND_USER_BY_LOGIN = "select role_name, user_id, login, password, salt, firstname, " +
            "lastname, email, birth from users join roles on users.role_id = roles.role_id and login = ?";
    private static final String FIND_USER_BY_EMAIL = "select role_name, user_id, login, password, salt, firstname, " +
            "lastname, email, birth from users join roles on users.role_id = roles.role_id and email = ?";
    private static final String FIND_PASSWORD_BY_LOGIN = "select password from users where login = ?";
    private static final String FIND_SALT_BY_LOGIN = "select salt from users where login = ?";
    private static final String CREATE_USER = "insert into users(login, password, salt, firstname, lastname, email, " +
            "birth, secret_word) values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PASSWORD_BY_EMAIL = "update users\n" +
            "set password = ?, salt = ? where email = ?";
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
    public Optional<User> findById(Long id) throws DaoException {
        return executeForSingleResult(FIND_USER_BY_ID, id);
    }

    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        return executeForSingleResult(FIND_USER_BY_LOGIN, login);
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
    public Optional<String> findSaltByLogin(String login) throws DaoException {
        return findString(FIND_SALT_BY_LOGIN, ColumnLabel.SALT, login);
    }

    @Override
    public boolean isAuthenticSecretWord(Long accountId, String secretWord) throws DaoException {
        return findBoolean(IF_PRESENT_BY_ACCOUNT_ID_AND_SECRET_WORD, ColumnLabel.EXISTS, accountId, secretWord);
    }

    @Override
    public void updatePassword(String email, String newPassword, String newSalt) throws DaoException {
        executeUpdate(UPDATE_PASSWORD_BY_EMAIL, newPassword, newSalt, email);
    }
}
