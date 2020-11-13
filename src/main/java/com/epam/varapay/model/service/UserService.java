package com.epam.varapay.model.service;

import com.epam.varapay.model.entity.User;
import com.epam.varapay.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

/**
 * @author Ivan Varabei
 * @version 1.0
 */
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
