package com.varabei.ivan.model.dao;

import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.entity.CustomCurrency;
import com.varabei.ivan.model.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

public interface BidDao {
    void placeTopUpBid(Long accountId, Long amount, BigDecimal amountInChosenCurrency,
                       CustomCurrency currency, String message) throws DaoException;

    void placeWithdrawBid(Long accountId, Long amount, String message) throws DaoException;

    List<Bid> findInProgressBids(int recordsPerPage, int page) throws DaoException;

    List<Bid> findByAccountId(Long accountId, int limit, int offset) throws DaoException;

    Long findAmountOfInProgressBids() throws DaoException;

    Long findAmountOfBidsByAccountId(Long accountId) throws DaoException;

    boolean isPresentInProgressBids(Long accountId) throws DaoException;

    void approveTopUpBid(Long topUpBidId) throws DaoException;

    void approveWithdrawBid(Long withdrawBidId) throws DaoException;

    void rejectTopUpBid(Long topUpBidId, String adminComment) throws DaoException;

    void rejectWithdrawBid(Long withdrawBidId, String adminComment) throws DaoException;
}
