package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface BidService {
    List<Bid> findInProgressBids() throws ServiceException;

    List<Bid> findByAccountId(Long accountId) throws ServiceException;

    void placeTopUpBid(Long accountId, BigDecimal amount, String message) throws ServiceException;

    void placeWithdrawBid(Long accountId, BigDecimal amount, String message) throws ServiceException;

    void approveTopUpBid(Long topUpBidId) throws ServiceException;

    void approveWithdrawBid(Long withdrawBidId) throws ServiceException;

    void rejectTopUpBid(Long topUpBidId, String adminComment) throws ServiceException;

    void rejectWithdrawBid(Long withdrawBidId, String adminComment) throws ServiceException;
}
