package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<String> checkSignupDataAndSendEmail(Map<String, String> signupData) throws ServiceException;

    Optional<String> signUp(User user, String password, String secretWord,
                            String tempCode, String originalTempCode) throws ServiceException;

    Optional<User> signIn(String login, String password) throws ServiceException;

    Optional<User> findById(Long id) throws ServiceException;

    Optional<User> findByLogin(String login) throws ServiceException;

    Optional<User> findByEmail(String email) throws ServiceException;

    boolean recoverPassword(String email) throws ServiceException;

    boolean updatePassword(Map<String, String> changePasswordData) throws ServiceException;

    boolean isAuthenticSecretWord(Long accountId, String secretWord) throws ServiceException;
}
