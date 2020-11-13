package com.epam.varapay.model.service;

import com.epam.varapay.model.entity.Payment;
import com.epam.varapay.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Provides methods having to do with {@link Payment}.
 *
 * @author Ivan Varabei
 * @version 1.0
 * @see com.epam.varapay.model.service.impl.PaymentServiceImpl
 */
public interface PaymentService {
    /**
     * Is used to pagination. Finds payment list belonging to the particular card.
     *
     * @param cardId         the target card`s identifier.
     * @param recordsPerPage amount of records per page.
     * @param page           the index of desirable page.
     * @return the list of payments of the particular card and page.
     * @throws ServiceException if the lower layer throws an exception.
     */
    List<Payment> findPaymentsByCardId(Long cardId, int recordsPerPage, int page) throws ServiceException;

    /**
     * Finds the amount of pages which is necessary to show all payments of the particular card.
     *
     * @param cardId         the target account`s identifier.
     * @param recordsPerPage amount of records per page.
     * @return the amount of pages.
     * @throws ServiceException if the lower layer throws an exception.
     */
    int findAmountOfPagesByCardId(Long cardId, int recordsPerPage) throws ServiceException;

    /**
     * Commits payment or set errors into paymentData map.
     *
     * @param paymentData contains
     *                    <ul>
     *                      <li>source card id</li>
     *                      <li>source card cvc</li>
     *                      <li>destination card number</li>
     *                      <li>destination card valid thru</li>
     *                      <li>amount</li>
     *                    </ul>
     * @return true if the payment was committed else false.
     * @throws ServiceException if the lower layer throws an exception.
     */
    boolean makePayment(Map<String, String> paymentData) throws ServiceException;
}
