package com.epam.varapay.model.service;

import com.epam.varapay.model.entity.Card;
import com.epam.varapay.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Provides methods having to do with {@link Card}.
 *
 * @author Ivan Varabei
 * @version 1.0
 * @see com.epam.varapay.model.service.impl.CardServiceImpl
 */
public interface CardService {
    /**
     * Creates card and returns cvc of newly created card.
     *
     * @param accountId the account`s identifier for witch the card is created.
     * @return the cvc of newly created card.
     * @throws ServiceException if the lower layer throws an exception.
     */
    String createCardAndReturnCvc(Long accountId) throws ServiceException;

    /**
     * Finds the
     *
     * @param cardId the target card`s identifier.
     * @return the Optional of the card
     * @throws ServiceException if the lower layer throws an exception.
     */
    Optional<Card> findById(Long cardId) throws ServiceException;

    /**
     * Finds list of the cards belonging to the particular account.
     *
     * @param accountId the target account`s identifier.
     * @return the list of the cards belonging to the account having accountId.
     * @throws ServiceException if the lower layer throws an exception.
     */
    List<Card> findByAccountId(Long accountId) throws ServiceException;

    /**
     * Deletes the card by cardId.
     *
     * @param cardId the target card`s identifier.
     * @throws ServiceException if the lower layer throws an exception.
     */
    void delete(Long cardId) throws ServiceException;
}
