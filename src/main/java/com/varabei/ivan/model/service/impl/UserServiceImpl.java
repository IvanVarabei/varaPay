package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.UserDao;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.DataTransferMapKey;
import com.varabei.ivan.model.service.MailService;
import com.varabei.ivan.model.service.UserService;
import com.varabei.ivan.util.CustomSecurity;
import com.varabei.ivan.validator.UserValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserValidator userValidator = new UserValidator();
    private static UserDao userDao = DaoFactory.getInstance().getUserDao();
    private MailService mailService;
    private static final String SUBJECT_VERIFICATION = "Email verification";
    private static final String BODY_VERIFICATION_CODE = "Hi! Your verification code is : %s. Enter code into the form.";
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
                mailService.sendEmail(signupData.get(DataTransferMapKey.EMAIL), SUBJECT_VERIFICATION,
                        String.format(BODY_VERIFICATION_CODE, tempCode));
                return Optional.of(tempCode);
            }
        }
        return Optional.empty();
    }

    @Override
    public void signUp(User user, String password, String secretWord) throws ServiceException {
        try {
            String salt = CustomSecurity.generateRandom(SALT_LENGTH);
            password = CustomSecurity.generateHash(password + salt);
            secretWord = CustomSecurity.generateHash(secretWord);
            userDao.create(user, password, salt, secretWord);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> signIn(String login, String password) throws ServiceException {
        try {
            Optional<String> hashedPassword = userDao.findPasswordByLogin(login);
            Optional<String> salt = userDao.findSaltByLoginOrEmail(login);
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
    public boolean updatePassword(Map<String, String> changePasswordData) throws ServiceException {
        Map<String, String> initialMap = new HashMap<>(changePasswordData);
        userValidator.checkPasswords(changePasswordData);
        if (signIn(changePasswordData.get(DataTransferMapKey.LOGIN),
                changePasswordData.get(DataTransferMapKey.OLD_PASSWORD)).isEmpty()) {
            changePasswordData.put(RequestParam.OLD_PASSWORD, ErrorInfo.OLD_PASSWORD.name().toLowerCase());
        }
        if (changePasswordData.equals(initialMap)) {
            User user = findByLogin(changePasswordData.get(DataTransferMapKey.LOGIN)).get();
            updatePassword(user.getEmail(), changePasswordData.get(DataTransferMapKey.PASSWORD));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updatePassword(String email, String newPassword) throws ServiceException {
        try {
            String salt = userDao.findSaltByLoginOrEmail(email).get();
            String hashedPassword = CustomSecurity.generateHash(newPassword + salt);
            userDao.updatePassword(email, hashedPassword, salt);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
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

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDao.findAll();
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
    public Optional<User> findById(Long id) throws ServiceException {
        try {
            return userDao.findById(id);
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
}