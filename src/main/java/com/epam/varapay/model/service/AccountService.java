package com.epam.varapay.model.service;

import com.epam.varapay.model.entity.Account;
import com.epam.varapay.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Provides methods having to do with {@link Account}.
 *
 * @author Ivan Varabei
 * @version 1.0
 * @see com.epam.varapay.model.service.impl.AccountServiceImpl
 */
public interface AccountService {
    /**
     * Creates a new account and binds it with the userId parameter.
     *
     * @param userId the reference to bind the newly created account.
     * @throws ServiceException if the lower layer throws an exception.
     */
    void create(Long userId) throws ServiceException;

    /**
     * @param accountId the id of the sought account.
     * @return the Optional of the sought account or empty Optional if that id does`t exist.
     * @throws ServiceException if the lower layer throws an exception.
     */
    Optional<Account> findById(Long accountId) throws ServiceException;

    /**
     * @param login the login of the sought account.
     * @return the Optional of the sought account or empty Optional if that login does`t exist.
     * @throws ServiceException if the lower layer throws an exception.
     */
    List<Account> findByUserLogin(String login) throws ServiceException;

    /**
     * Finds disabled accounts by query.
     *
     * @param query the query can be the user login or accountId directly.
     * @return the list of accounts if the query is login and this user has some disabled accounts. Returns the list of
     * single account if the query is accountId.
     * @throws ServiceException if the lower layer throws an exception.
     */
    List<Account> findDisabledByLoginOrAccountId(String query) throws ServiceException;

    /**
     * Enable or disable account.
     *
     * @param accountId the target account`s identifier.
     * @throws ServiceException if the lower layer throws an exception.
     */
    void changeActive(Long accountId) throws ServiceException;

    /**
     * Deletes account by account id if account has zero balance and does not have in progress top up / withdraw bids.
     *
     * @param accountId the target account`s identifier.
     * @return the Optional containing error message if the account has non-zero balance or the account has in progress
     * top up / withdraw bids. Returns empty Optional if the account is deleted.
     * @throws ServiceException if the lower layer throws an exception.
     */
    Optional<String> delete(Long accountId) throws ServiceException;
}
