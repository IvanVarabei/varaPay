package com.epam.varapay.model.service.impl;

import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.model.dao.DaoFactory;
import com.epam.varapay.model.dao.UserDao;
import com.epam.varapay.model.entity.User;
import com.epam.varapay.exception.DaoException;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.DataTransferMapKey;
import com.epam.varapay.model.service.ErrorMessage;
import com.epam.varapay.model.service.UserService;
import com.epam.varapay.model.validator.UserValidator;
import com.epam.varapay.util.CustomSecurity;
import com.epam.varapay.util.MailSender;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserValidator userValidator = UserValidator.getInstance();
    private static UserDao userDao = DaoFactory.getInstance().getUserDao();
    private static MailSender mailSender = MailSender.getInstance();
    private static final String MAIL_SUBJECT_VERIFICATION = "Email verification";
    private static final String MAIL_SUBJECT_NEW_PASSWORD = "Your new password";
    private static final String MAIL_BODY_VERIFICATION = "Hi! Your verification code is : %s. " +
            "Enter code into the form.";
    private static final String MAIL_BODY_NEW_PASSWORD = "Hi! Your new password is : %s. " +
            "You can change password in your profile.";
    private static final int DEFAULT_PASSWORD_LENGTH = 20;
    private static final int VERIFICATION_CODE_LENGTH = 4;
    private static final int SALT_LENGTH = 64;

    @Override
    public Optional<String> checkSignupDataAndSendEmail(Map<String, String> signupData) throws ServiceException {
        Map<String, String> initialMap = new HashMap<>(signupData);
        if (userValidator.isValidSignupData(signupData)) {
            if (findByEmail(signupData.get(DataTransferMapKey.EMAIL)).isPresent()) {
                signupData.put(DataTransferMapKey.EMAIL, ErrorMessage.ALREADY_EXISTS.toString());
            }
            if (findByLogin(signupData.get(DataTransferMapKey.LOGIN)).isPresent()) {
                signupData.put(DataTransferMapKey.LOGIN, ErrorMessage.ALREADY_EXISTS.toString());
            }
            if (initialMap.equals(signupData)) {
                String tempCode = CustomSecurity.generateRandom(VERIFICATION_CODE_LENGTH);
                mailSender.sendEmail(signupData.get(DataTransferMapKey.EMAIL), MAIL_SUBJECT_VERIFICATION,
                        String.format(MAIL_BODY_VERIFICATION, tempCode));
                return Optional.of(tempCode);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> signUp(User user, String password, String secretWord, String tempCode,
                                   String originalTempCode) throws ServiceException {
        Optional<String> error = Optional.empty();
        try {
            if (tempCode.equals(originalTempCode)) {
                if (userDao.findByLogin(user.getLogin()).isPresent()) {
                    error = Optional.of(ErrorMessage.LOGIN_OCCUPIED.toString());
                } else {
                    String salt = CustomSecurity.generateRandom(SALT_LENGTH);
                    password = CustomSecurity.generateHash(password + salt);
                    secretWord = CustomSecurity.generateHash(secretWord);
                    userDao.create(user, password, salt, secretWord);
                }
            } else {
                error = Optional.of(ErrorMessage.TEMP_CODE.toString());
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return error;
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        try {
            Optional<String> hashedPassword = userDao.findPasswordByLogin(login);
            Optional<String> salt = userDao.findSaltByLogin(login);
            if (hashedPassword.isPresent() && salt.isPresent() &&
                    hashedPassword.get().equals(CustomSecurity.generateHash(password + salt.get()))) {
                return userDao.findByLogin(login);
            }
            return Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) throws ServiceException {
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        try {
            return userDao.findByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws ServiceException {
        try {
            return userDao.findByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean recoverPassword(String email) throws ServiceException {
        if (findByEmail(email).isPresent()) {
            String newPassword = CustomSecurity.generateRandom(DEFAULT_PASSWORD_LENGTH);
            mailSender.sendEmail(email, MAIL_SUBJECT_NEW_PASSWORD, String.format(MAIL_BODY_NEW_PASSWORD, newPassword));
            updatePassword(email, newPassword);
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePassword(Map<String, String> changePasswordData) throws ServiceException {
        String login = changePasswordData.get(DataTransferMapKey.LOGIN);
        String oldPassword = changePasswordData.get(DataTransferMapKey.OLD_PASSWORD);
        Optional<User> user = signIn(login, oldPassword);
        if (userValidator.checkPasswords(changePasswordData) && user.isPresent()) {
            updatePassword(user.get().getEmail(), changePasswordData.get(DataTransferMapKey.PASSWORD));
            return true;
        }
        if (user.isEmpty()) {
            changePasswordData.put(RequestParam.OLD_PASSWORD, ErrorMessage.OLD_PASSWORD.toString());
        }
        return false;
    }

    @Override
    public boolean isAuthenticSecretWord(Long accountId, String secretWord) throws ServiceException {
        try {
            secretWord = CustomSecurity.generateHash(secretWord);
            return userDao.isAuthenticSecretWord(accountId, secretWord);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void updatePassword(String email, String newPassword) throws ServiceException {
        try {
            String salt = CustomSecurity.generateRandom(SALT_LENGTH);
            String hashedPassword = CustomSecurity.generateHash(newPassword + salt);
            userDao.updatePassword(email, hashedPassword, salt);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}