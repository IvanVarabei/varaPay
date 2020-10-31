package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.DataTransferMapKey;
import com.varabei.ivan.model.service.ErrorInfo;
import com.varabei.ivan.model.service.MailService;
import com.varabei.ivan.model.service.UserService;
import com.varabei.ivan.model.validator.UserValidator;
import com.varabei.ivan.util.CustomSecurity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserValidator userValidator = new UserValidator();
    private static UserDao userDao = DaoFactory.getInstance().getUserDao();
    private MailService mailService;
    private static final String MAIL_SUBJECT_VERIFICATION = "Email verification";
    private static final String MAIL_SUBJECT_NEW_PASSWORD = "Your new password";
    private static final String MAIL_BODY_VERIFICATION = "Hi! Your verification code is : %s. " +
            "Enter code into the form.";
    private static final String MAIL_BODY_NEW_PASSWORD = "Hi! Your new password is : %s. " +
            "You can change password in your profile.";
    private static final int DEFAULT_PASSWORD_LENGTH = 20;
    private static final int VERIFICATION_CODE_LENGTH = 4;
    private static final int SALT_LENGTH = 64;

    public UserServiceImpl(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public Optional<String> checkSignupDataAndSendEmail(Map<String, String> signupData) throws ServiceException {
        Map<String, String> initialMap = new HashMap<>(signupData);
        if (userValidator.isValidSignupData(signupData)) {
            if (findByEmail(signupData.get(DataTransferMapKey.EMAIL)).isPresent()) {
                signupData.put(DataTransferMapKey.EMAIL, ErrorInfo.ALREADY_EXISTS.toString());
            }
            if (findByLogin(signupData.get(DataTransferMapKey.LOGIN)).isPresent()) {
                signupData.put(DataTransferMapKey.LOGIN, ErrorInfo.ALREADY_EXISTS.toString());
            }
            if (initialMap.equals(signupData)) {
                String tempCode = CustomSecurity.generateRandom(VERIFICATION_CODE_LENGTH);
                mailService.sendEmail(signupData.get(DataTransferMapKey.EMAIL), MAIL_SUBJECT_VERIFICATION,
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
                    error = Optional.of(ErrorInfo.LOGIN_OCCUPIED.toString());
                } else {
                    String salt = CustomSecurity.generateRandom(SALT_LENGTH);
                    password = CustomSecurity.generateHash(password + salt);
                    secretWord = CustomSecurity.generateHash(secretWord);
                    userDao.create(user, password, salt, secretWord);
                }
            } else {
                error = Optional.of(ErrorInfo.TEMP_CODE.toString());
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
            mailService.sendEmail(email, MAIL_SUBJECT_NEW_PASSWORD, String.format(MAIL_BODY_NEW_PASSWORD, newPassword));
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
        changePasswordData.put(RequestParam.OLD_PASSWORD, ErrorInfo.OLD_PASSWORD.toString());
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