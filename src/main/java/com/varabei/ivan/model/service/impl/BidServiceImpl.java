package com.varabei.ivan.model.service.impl;

import com.varabei.ivan.model.dao.AccountDao;
import com.varabei.ivan.model.dao.BidDao;
import com.varabei.ivan.model.dao.DaoFactory;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.entity.CustomCurrency;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.BidService;
import com.varabei.ivan.model.service.CurrencyService;
import com.varabei.ivan.model.service.DataTransferMapKey;
import com.varabei.ivan.model.service.ErrorInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BidServiceImpl implements BidService {
    private static BidDao bidDao = DaoFactory.getInstance().getTopUpBidDao();
    private static AccountDao accountDao = DaoFactory.getInstance().getAccountDao();
    private CurrencyService currencyService;

    public BidServiceImpl(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public boolean placeTopUpBid(Long accountId, BigDecimal amount, BigDecimal amountInChosenCurrency,
                                 CustomCurrency currency, String message) throws ServiceException {
        try {
            if (message != null && message.length() > 0) {
                bidDao.placeTopUpBid(accountId, amount.movePointRight(2).longValue(),
                        amountInChosenCurrency, currency, message);
                return true;
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
        return false;
    }

    @Override
    public Optional<BigDecimal> findAmountInChosenCurrencyIfEnoughBalance(Map<String, String> dataToConvert)
            throws ServiceException {
        Optional<BigDecimal> amountInChosenCurrency = currencyService.convertUsdToAnotherCurrency(dataToConvert);
        if (amountInChosenCurrency.isPresent()) {
            try {
                BigDecimal amountToWithdraw = new BigDecimal(dataToConvert.get(DataTransferMapKey.AMOUNT));
                Long accountId = Long.parseLong(dataToConvert.get(DataTransferMapKey.ACCOUNT_ID));
                Account account = accountDao.findById(accountId).get();
                if (account.getBalance().compareTo(amountToWithdraw) >= 0) {
                    return amountInChosenCurrency;
                }
                dataToConvert.put(DataTransferMapKey.AMOUNT, ErrorInfo.NOT_ENOUGH_BALANCE.toString());
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean placeWithdrawBid(Long accountId, BigDecimal amount, BigDecimal amountInChosenCurrency,
                                    CustomCurrency currency, String message) throws ServiceException {
        try {
            if (message != null && message.length() > 0) {
                bidDao.placeWithdrawBid(accountId, amount.movePointRight(2).longValue(),
                        amountInChosenCurrency, currency, message);
                return true;
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
        return false;
    }

    @Override
    public List<Bid> findInProgressBids(int limit, int pageIndex) throws ServiceException {
        try {
            return bidDao.findInProgressBids(limit, (pageIndex - 1) * limit);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Bid> findByAccountId(Long accountId, int limit, int pageIndex) throws ServiceException {
        try {
            return bidDao.findByAccountId(accountId, limit, (pageIndex - 1) * limit);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public int findAmountOfPages(int limit) throws ServiceException {
        try {
            Long numberOfRecords = bidDao.findAmountOfInProgressBids();
            return (int) Math.ceil(numberOfRecords * 1d / limit);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public int findAmountOfPagesByAccountId(Long accountId, int limit) throws ServiceException {
        try {
            Long numberOfRecords = bidDao.findAmountOfBidsByAccountId(accountId);
            return (int) Math.ceil(numberOfRecords * 1d / limit);
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void approveBid(Long bidId) throws ServiceException {
        try {
            if (bidDao.isTopUpBid(bidId)) {
                bidDao.approveTopUpBid(bidId);
            } else {
                bidDao.approveWithdrawBid(bidId);
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void rejectBid(Long bidId, String adminComment) throws ServiceException {
        try {
            if (bidDao.isTopUpBid(bidId)) {
                bidDao.rejectTopUpBid(bidId, adminComment);
            } else {
                bidDao.rejectWithdrawBid(bidId, adminComment);
            }
        } catch (DaoException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
