package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.dao.BidDao;
import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.BidService;

import java.util.List;

public class BidServiceImpl implements BidService {
    private static final BidDao BID_DAO = DaoFactory.getInstance().getTopUpBidDao();

    @Override
    public List<Bid> findAll() throws ServiceException {
        try {
            return BID_DAO.findAll();
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void placeTopUpBid(Long accountId, Long amount, String message) throws ServiceException {
        try {
            BID_DAO.placeTopUpBid(accountId, amount, message);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void approveTopUpBid(Long topUpBidId) throws ServiceException {
        try {
            BID_DAO.approveTopUpBid(topUpBidId);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void rejectTopUpBid(Long topUpBidId) throws ServiceException {

    }
}
