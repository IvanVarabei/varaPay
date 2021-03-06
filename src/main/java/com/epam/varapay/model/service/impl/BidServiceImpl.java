package com.epam.varapay.model.service.impl;

import com.epam.varapay.model.dao.AccountDao;
import com.epam.varapay.model.dao.BidDao;
import com.epam.varapay.model.dao.DaoFactory;
import com.epam.varapay.model.entity.Account;
import com.epam.varapay.model.entity.Bid;
import com.epam.varapay.model.entity.CustomCurrency;
import com.epam.varapay.exception.DaoException;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.BidService;
import com.epam.varapay.model.service.DataTransferMapKey;
import com.epam.varapay.model.service.ErrorMessage;
import com.epam.varapay.util.CurrencyConverter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BidServiceImpl implements BidService {
    private BidDao bidDao = DaoFactory.getInstance().getTopUpBidDao();
    private static AccountDao accountDao = DaoFactory.getInstance().getAccountDao();
    private static CurrencyConverter currencyConverter = CurrencyConverter.getInstance();

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
        Optional<BigDecimal> amountInChosenCurrency = currencyConverter.convertUsdToAnotherCurrency(dataToConvert);
        if (amountInChosenCurrency.isPresent()) {
            try {
                BigDecimal amountToWithdraw = new BigDecimal(dataToConvert.get(DataTransferMapKey.AMOUNT));
                Long accountId = Long.parseLong(dataToConvert.get(DataTransferMapKey.ACCOUNT_ID));
                Account account = accountDao.findById(accountId).get();
                if (account.getBalance().compareTo(amountToWithdraw) >= 0) {
                    return amountInChosenCurrency;
                }
                dataToConvert.put(DataTransferMapKey.AMOUNT, ErrorMessage.NOT_ENOUGH_BALANCE.toString());
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
    public void approveBid(Long bidId, String adminComment) throws ServiceException {
        try {
            if (bidDao.isTopUpBid(bidId)) {
                bidDao.approveTopUpBid(bidId, adminComment);
            } else {
                bidDao.approveWithdrawBid(bidId, adminComment);
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
