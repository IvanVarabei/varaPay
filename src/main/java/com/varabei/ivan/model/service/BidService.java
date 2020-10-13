package com.varabei.ivan.model.service;

import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.exception.ServiceException;

import java.util.List;

public interface BidService {
    List<Bid> findAll() throws ServiceException;

    void placeTopUpBid(Long accountId, Long amount, String message) throws ServiceException;

    void approveTopUpBid(Long topUpBidId) throws ServiceException;

    void rejectTopUpBid(Long topUpBidId) throws ServiceException;
}
