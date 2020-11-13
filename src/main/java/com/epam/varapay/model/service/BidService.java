package com.epam.varapay.model.service;

import com.epam.varapay.model.entity.Bid;
import com.epam.varapay.model.entity.CustomCurrency;
import com.epam.varapay.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Provides methods having to do with {@link Bid}.
 *
 * @author Ivan Varabei
 * @version 1.0
 * @see com.epam.varapay.model.service.impl.BidServiceImpl
 * @see com.epam.varapay.model.entity.BidState
 */
public interface BidService {
    /**
     * Places a new top up bid.
     *
     * @param accountId              the target account`s identifier.
     * @param amount                 the usd amount to increase account balance.
     * @param amountInChosenCurrency the amount of currency which client has chosen to top up his account.
     * @param currency               the currency which client has chosen to top up his account.
     * @param message                the message should contain the reference to transaction in blockchain browser.
     * @return true if the message is not empty and the bid was placed.
     * @throws ServiceException if the lower layer throws an exception.
     */
    boolean placeTopUpBid(Long accountId, BigDecimal amount, BigDecimal amountInChosenCurrency,
                          CustomCurrency currency, String message) throws ServiceException;

    /**
     * Checks If enough balance to place withdraw bid. If not, sets error in dataToConvert map.
     *
     * @param dataToConvert contains
     *                      <ul>
     *                       <li>USD amount</li>
     *                       <li>Account id</li>
     *                       <li>Chosen currency</li>
     *                      </ul>
     * @return If enough balance to place withdraw bid returns amount in chosen currency else returns Optional empty.
     * @throws ServiceException if the lower layer throws an exception.
     */
    Optional<BigDecimal> findAmountInChosenCurrencyIfEnoughBalance(Map<String, String> dataToConvert)
            throws ServiceException;

    /**
     * Places a new withdraw bid.
     *
     * @param accountId              the target account`s identifier.
     * @param amount                 the usd amount to decrease account balance.
     * @param amountInChosenCurrency the amount of currency which client has chosen to withdraw.
     * @param currency               the currency which client has chosen to withdraw.
     * @param message                the message should contain the client`s wallet and can contain other info.
     * @return true if the message is not empty and the bid was placed.
     * @throws ServiceException if the lower layer throws an exception.
     */
    boolean placeWithdrawBid(Long accountId, BigDecimal amount, BigDecimal amountInChosenCurrency,
                             CustomCurrency currency, String message) throws ServiceException;

    /**
     * Is used to pagination. Finds particular amount of records from specified page.
     *
     * @param recordsPerPage amount of records per page.
     * @param page           the index of desirable page.
     * @return the list of bids of the particular page.
     * @throws ServiceException if the lower layer throws an exception.
     */
    List<Bid> findInProgressBids(int recordsPerPage, int page) throws ServiceException;

    /**
     * Is used to pagination. Finds bid list belonging to the particular account.
     *
     * @param accountId      the target account`s identifier.
     * @param recordsPerPage amount of records per page.
     * @param page           the index of desirable page.
     * @return the list of bids of the particular account and page.
     * @throws ServiceException if the lower layer throws an exception.
     */
    List<Bid> findByAccountId(Long accountId, int recordsPerPage, int page) throws ServiceException;

    /**
     * Finds the amount of pages which is necessary to show all bids of each account.
     *
     * @param recordsPerPage amount of records per page.
     * @return the amount of pages.
     * @throws ServiceException if the lower layer throws an exception.
     */
    int findAmountOfPages(int recordsPerPage) throws ServiceException;

    /**
     * Finds the amount of pages which is necessary to show all bids of the particular account.
     *
     * @param accountId the target account`s identifier.
     * @param recordsPerPage amount of records per page.
     * @return the amount of pages.
     * @throws ServiceException if the lower layer throws an exception.
     */
    int findAmountOfPagesByAccountId(Long accountId, int recordsPerPage) throws ServiceException;

    /**
     * Increases account balance if the bid is top up. Decreases account balance if the bid is withdraw. Sets
     * {@link com.epam.varapay.model.entity.BidState} approved.
     *
     * @param bidId the target bid`s identifier.
     * @param adminComment the comment obout the operation.
     * @throws ServiceException if the lower layer throws an exception.
     */
    void approveBid(Long bidId, String adminComment) throws ServiceException;

    /**
     * Increases account balance if the bid is withdraw. Sets {@link com.epam.varapay.model.entity.BidState}  rejected.
     *
     * @param bidId the target bid`s identifier.
     * @param adminComment the comment obout the operation.
     * @throws ServiceException if the lower layer throws an exception.
     */
    void rejectBid(Long bidId, String adminComment) throws ServiceException;
}
