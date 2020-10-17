package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.BidDao;
import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.BidService;

import java.math.BigDecimal;
import java.util.List;

public class BidServiceImpl implements BidService {
    private static final BidDao bidDao = DaoFactory.getInstance().getTopUpBidDao();

    @Override
    public List<Bid> findInProgressBids() throws ServiceException {
        try {
            return bidDao.findInProgressBids();
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Bid> findByAccountId(Long accountId) throws ServiceException {
        try {
            return bidDao.findByAccountId(accountId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void placeTopUpBid(Long accountId, BigDecimal amount, String message) throws ServiceException {
        try {
            bidDao.placeTopUpBid(accountId,  amount.movePointRight(2).longValue(), message);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void placeWithdrawBid(Long accountId, BigDecimal amount, String message) throws ServiceException {
        try {
            bidDao.placeWithdrawBid(accountId,  amount.movePointRight(2).longValue(), message);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void approveTopUpBid(Long topUpBidId) throws ServiceException {
        try {
            bidDao.approveTopUpBid(topUpBidId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void approveWithdrawBid(Long withdrawBidId) throws ServiceException {
        try {
            bidDao.approveWithdrawBid(withdrawBidId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void rejectTopUpBid(Long topUpBidId, String adminComment) throws ServiceException {
        try {
            bidDao.rejectTopUpBid(topUpBidId, adminComment);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void rejectWithdrawBid(Long withdrawBidId, String adminComment) throws ServiceException {
        try {
            bidDao.rejectWithdrawBid(withdrawBidId, adminComment);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
