package com.epam.varapay.model.dao;

import com.epam.varapay.model.entity.Bid;
import com.epam.varapay.model.entity.CustomCurrency;
import com.epam.varapay.exception.DaoException;

import java.math.BigDecimal;
import java.util.List;

public interface BidDao {
    void placeTopUpBid(Long accountId, Long amount, BigDecimal amountInChosenCurrency,
                       CustomCurrency currency, String message) throws DaoException;

    void placeWithdrawBid(Long accountId, Long amount, BigDecimal amountInChosenCurrency,
                          CustomCurrency currency, String message) throws DaoException;

    List<Bid> findInProgressBids(int limit, int offset) throws DaoException;

    List<Bid> findByAccountId(Long accountId, int limit, int offset) throws DaoException;

    Long findAmountOfInProgressBids() throws DaoException;

    Long findAmountOfBidsByAccountId(Long accountId) throws DaoException;

    boolean isPresentInProgressBids(Long accountId) throws DaoException;

    boolean isTopUpBid(Long bidId) throws DaoException;

    void approveTopUpBid(Long topUpBidId, String adminComment) throws DaoException;

    void approveWithdrawBid(Long withdrawBidId, String adminComment) throws DaoException;

    void rejectTopUpBid(Long topUpBidId, String adminComment) throws DaoException;

    void rejectWithdrawBid(Long withdrawBidId, String adminComment) throws DaoException;
}
