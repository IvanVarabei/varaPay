package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.entity.CustomCurrency;
import com.varabei.ivan.model.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
