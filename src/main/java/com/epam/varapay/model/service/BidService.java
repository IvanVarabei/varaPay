package com.epam.varapay.model.service;

import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.entity.Bid;
import com.epam.varapay.model.entity.CustomCurrency;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Ivan Varabei
 * @version 1.0
 */
public interface BidService {
    boolean placeTopUpBid(Long accountId, BigDecimal amount, BigDecimal amountInChosenCurrency,
                          CustomCurrency currency, String message) throws ServiceException;

    Optional<BigDecimal> findAmountInChosenCurrencyIfEnoughBalance(Map<String, String > dataToConvert) throws ServiceException;

    boolean placeWithdrawBid(Long accountId, BigDecimal amount, BigDecimal amountInChosenCurrency,
                          CustomCurrency currency, String message) throws ServiceException;

    List<Bid> findInProgressBids(int recordsPerPage, int page) throws ServiceException;

    List<Bid> findByAccountId(Long accountId, int recordsPerPage, int page) throws ServiceException;

    int findAmountOfPages(int recordsPerPage) throws ServiceException;

    int findAmountOfPagesByAccountId(Long accountId, int recordsPerPage) throws ServiceException;

    void approveBid(Long bidId) throws ServiceException;

    void rejectBid(Long bidId, String adminComment) throws ServiceException;
}
