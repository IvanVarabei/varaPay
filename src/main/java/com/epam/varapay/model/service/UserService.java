package com.epam.varapay.model.service;

import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.entity.User;

import java.util.Map;
import java.util.Optional;

/**
 * Provides methods having to do with {@link User}.
 *
 * @author Ivan Varabei
 * @version 1.0
 * @see com.epam.varapay.model.service.impl.UserServiceImpl
 * @see com.epam.varapay.util.MailSender
 */
public interface UserService {
    /**
     * Checks signup data. If all right then sends email verification code.
     *
     * @param signupData contains
     *                   <ul>
     *                     <li>login</li>
     *                     <li>password</li>
     *                     <li>repeat password</li>
     *                     <li>firstname</li>
     *                     <li>lastname</li>
     *                     <li>email</li>
     *                     <li>birth date</li>
     *                     <li>secret word</li>
     *                   </ul>
     * @return if all right returns Optional of verification temporary code otherwise returns empty Optional.
     * @throws ServiceException if the lower layer throws an exception.
     */
    Optional<String> checkSignupDataAndSendEmail(Map<String, String> signupData) throws ServiceException;

    /**
     * Checks verification temporary code and registers a new user.
     *
     * @param user             user instance for registration.
     * @param password         user`s password.
     * @param secretWord       user`s secret word.
     * @param tempCode         verification temporary code entered by user.
     * @param originalTempCode original verification temporary code.
     * @return if all right returns empty Optional else returns Optional containing an error.
     * @throws ServiceException if the lower layer throws an exception.
     */
    Optional<String> signUp(User user, String password, String secretWord,
                            String tempCode, String originalTempCode) throws ServiceException;

    /**
     * Returns user instance if the login and the password are right.
     *
     * @param login    user`s login.
     * @param password user`s password.
     * @return if all right returns Optional containing the user else returns empty optional.
     * @throws ServiceException if the lower layer throws an exception.
     */
    Optional<User> signIn(String login, String password) throws ServiceException;

    /**
     * Finds user by user`s id.
     *
     * @param id user`s ud.
     * @return Optional of user having this id.
     * @throws ServiceException if the lower layer throws an exception.
     */
    Optional<User> findById(Long id) throws ServiceException;

    /**
     * Finds user by user`s login.
     *
     * @param login user`s login.
     * @return Optional of user having this login.
     * @throws ServiceException if the lower layer throws an exception.
     */
    Optional<User> findByLogin(String login) throws ServiceException;

    /**
     * Finds user by user`s email.
     *
     * @param email user`s email.
     * @return Optional of user having this email.
     * @throws ServiceException if the lower layer throws an exception.
     */
    Optional<User> findByEmail(String email) throws ServiceException;

    /**
     * Creates new random password. Saves the new password. Sends an email containing the new password.
     *
     * @param email user`s email.
     * @return true if the email is present else false.
     * @throws ServiceException if the lower layer throws an exception.
     */
    boolean recoverPassword(String email) throws ServiceException;

    /**
     * Changes user`s password if credentials are right and new password is valid.
     *
     * @param changePasswordData contains
     *                           <ul>
     *                             <li>old password</li>
     *                             <li>password</li>
     *                             <li>repeat password</li>
     *                             <li>login</li>
     *                           </ul>
     * @return true if the changePasswordData is right and the password was changed else false.
     * @throws ServiceException if the lower layer throws an exception.
     */
    boolean updatePassword(Map<String, String> changePasswordData) throws ServiceException;

    /**
     * Checks if the secret word is associated with the particular account.
     *
     * @param accountId  user`s account id.
     * @param secretWord estimated secret word.
     * @return true if the secret word is right else false.
     * @throws ServiceException if the lower layer throws an exception.
     */
    boolean isAuthenticSecretWord(Long accountId, String secretWord) throws ServiceException;
}
