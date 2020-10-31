package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.entity.CustomCurrency;
import com.varabei.ivan.model.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface BidService {
    boolean placeTopUpBid(Long accountId, BigDecimal amount, BigDecimal amountInChosenCurrency,
                          CustomCurrency currency, String message) throws ServiceException;

    void placeWithdrawBid(Long accountId, BigDecimal amount, String message) throws ServiceException;

    List<Bid> findInProgressBids(int recordsPerPage, int page) throws ServiceException;

    List<Bid> findByAccountId(Long accountId, int recordsPerPage, int page) throws ServiceException;

    int findAmountOfPages(int recordsPerPage) throws ServiceException;

    int findAmountOfPagesByAccountId(Long accountId, int recordsPerPage) throws ServiceException;

    void approveTopUpBid(Long topUpBidId) throws ServiceException;

    void approveWithdrawBid(Long withdrawBidId) throws ServiceException;

    void rejectTopUpBid(Long topUpBidId, String adminComment) throws ServiceException;

    void rejectWithdrawBid(Long withdrawBidId, String adminComment) throws ServiceException;
}
