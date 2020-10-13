package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.exception.DaoException;
import com.varabei.ivan.model.dao.BidDao;
import com.varabei.ivan.model.entity.AccountInfo;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbBidDao extends GenericDao implements BidDao {
    private static final String FIND_TOP_UP_BIDS = "select accounts.account_id, accounts.balance, accounts.is_active," +
            " bids.bid_id, bids.amount, bids.placing_date_time " +
            "from bids join accounts on bids.account_id = accounts.account_id " +
            "and is_top_up = true and bids.is_abandoned = false";
    private static final String FIND_WITHDRAW_BIDS = "select accounts.account_id, accounts.balance, accounts.is_active," +
            " bids.bid_id, bids.amount, bids.placing_date_time " +
            "from bids join accounts on bids.account_id = accounts.account_id " +
            "and is_top_up = false and bids.is_abandoned = false";
    private static final String FIND_ALL_BIDS = "select accounts.account_id, accounts.balance, accounts.is_active," +
            " bids.bid_id, bids.amount, bids.placing_date_time " +
            "from bids join accounts on bids.account_id = accounts.account_id " +
            "and bids.is_abandoned = false";
    private static final String FIND_BID_BY_ID = "select accounts.account_id, accounts.balance, accounts.is_active," +
            " bids.bid_id, bids.amount, bids.placing_date_time " +
            "from bids join accounts on bids.account_id = accounts.account_id and bid_id = ?";
    private static final String PLACE_TOP_UP_BID = "insert into bids (account_id, amount, is_top_up, message) " +
            "VALUES (?, ?, true, ?)";
    private static final String PLACE_WITHDRAW_BID = "insert into bids (account_id, amount, is_top_up, message) " +
            "VALUES (?, ?, false, ?);";
    private static final String ADD_ACCOUNT_BALANCE = "update accounts set balance = balance + ? where account_id = ?";
    private static final String ABANDON_BID = "update bids set is_abandoned = true where bid_id = ?";
    private static final String UPDATE_ADMIN_COMMENT = "update bids set admin_comment = ? where bid_id = ?";
    private static final String SET_STATE_REJECTED = "update bids set bid_state_id = 3 where bid_id = ?";
    private static final String SET_STATE_APPROVED = "update bids set bid_state_id = 2 where bid_id = ?";
    private static final String RECOVER_ACCOUNT_BALANCE_CONSIDERING_WITHDRAW_BID_ID =
            "update accounts set balance = balance + (select amount from bids where bid_id = ?) \n" +
                    "where account_id = (select account_id from bids where bid_id = ?)";

    @Override
    public List<Bid> findAll() throws DaoException {
        Connection connection = pool.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        List<Bid> bids = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL_BIDS);
            while (resultSet.next()) {
                Bid bid = instantiateTopUpBid(resultSet);
                bids.add(bid);
            }
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(resultSet, daoException);
            } finally {
                try {
                    closeResource(statement, daoException);
                } finally {
                    pool.releaseConnection(connection);
                }
            }
        }
        return bids;
    }

    @Override
    public void placeTopUpBid(Long accountId, Long amount, String message) throws DaoException {
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(PLACE_TOP_UP_BID);
            preparedStatement.setLong(PARAM_INDEX_1, accountId);
            preparedStatement.setLong(PARAM_INDEX_2, amount);
            preparedStatement.setString(PARAM_INDEX_3, message);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(preparedStatement, daoException);
            } finally {
                pool.releaseConnection(connection);
            }
        }
    }

    @Override
    public void placeWithdrawBid(Long accountId, Long amount, String message) throws DaoException {
        Connection connection = pool.getConnection();
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        try {
            preparedStatement = connection.prepareStatement(PLACE_WITHDRAW_BID);
            preparedStatement.setLong(PARAM_INDEX_1, accountId);
            preparedStatement.setLong(PARAM_INDEX_2, amount);
            preparedStatement.setString(PARAM_INDEX_3, message);
            preparedStatement.executeUpdate();
            executeUpdate(ADD_ACCOUNT_BALANCE, connection, -amount, accountId);
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(preparedStatement, daoException);
            } finally {
                pool.releaseConnection(connection);
            }
        }
    }

    @Override
    public void approveTopUpBid(Long topUpBidId) throws DaoException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DaoException daoException = null;
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(FIND_BID_BY_ID);
            preparedStatement.setLong(PARAM_INDEX_1, topUpBidId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Bid bid = instantiateTopUpBid(resultSet);
            executeUpdate(ADD_ACCOUNT_BALANCE, connection, bid.getAmount().longValue(), bid.getAccountInfo().getId());
            executeUpdate(SET_STATE_APPROVED, connection, topUpBidId);
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(resultSet, daoException);
            } finally {
                try {
                    closeResource(preparedStatement, daoException);
                } finally {
                    pool.releaseConnection(connection);
                }
            }
        }
    }

    @Override
    public void approveWithdrawBid(Long withdrawBidId) throws DaoException {
        executeUpdate(SET_STATE_APPROVED, withdrawBidId);
    }

    @Override
    public void rejectTopUpBid(Long topUpBidId, String adminComment) throws DaoException {
        PreparedStatement preparedStatement = null;
        DaoException daoException = null;
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(UPDATE_ADMIN_COMMENT);
            preparedStatement.setString(PARAM_INDEX_1, adminComment);
            preparedStatement.setLong(PARAM_INDEX_2, topUpBidId);
            preparedStatement.execute();
            executeUpdate(SET_STATE_REJECTED, connection, topUpBidId);
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(preparedStatement, daoException);
            } finally {
                pool.releaseConnection(connection);
            }
        }
    }

    @Override
    public void rejectWithdrawBid(Long withdrawBidId, String adminComment) throws DaoException {
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        DaoException daoException = null;
        Connection connection = pool.getConnection();
        try {
            preparedStatement = connection.prepareStatement(UPDATE_ADMIN_COMMENT);
            preparedStatement.setString(PARAM_INDEX_1, adminComment);
            preparedStatement.setLong(PARAM_INDEX_2, withdrawBidId);
            preparedStatement.execute();
            preparedStatement1 = connection.prepareStatement(RECOVER_ACCOUNT_BALANCE_CONSIDERING_WITHDRAW_BID_ID);
            preparedStatement1.setLong(PARAM_INDEX_1, withdrawBidId);
            preparedStatement1.setLong(PARAM_INDEX_2, withdrawBidId);
            preparedStatement1.execute();
            executeUpdate(SET_STATE_REJECTED, connection, withdrawBidId);
        } catch (SQLException e) {
            daoException = new DaoException("can not get access to db", e);
        } finally {
            try {
                closeResource(preparedStatement, daoException);
            } finally {
                try {
                    closeResource(preparedStatement1, daoException);
                } finally {
                    pool.releaseConnection(connection);
                }
            }
        }
    }

    private Bid instantiateTopUpBid(ResultSet resultSet) throws SQLException {
        Bid bid = new Bid();
        AccountInfo accountInfo = new AccountInfo();
        bid.setAccountInfo(accountInfo);
        accountInfo.setId(resultSet.getLong(Const.AccountField.ID));
        accountInfo.setBalance(new BigDecimal(resultSet.getLong(Const.AccountField.BALANCE)));
        accountInfo.setActive(resultSet.getBoolean(Const.AccountField.IS_ACTIVE));
        bid.setId(resultSet.getLong(Const.BidField.ID));
        bid.setAmount(BigDecimal.valueOf(resultSet.getLong("amount")));
        bid.setPlacingDateTime((resultSet.getTimestamp(Const.BidField.PLACING_DATE_TIME).toLocalDateTime()));
        return bid;
    }
}
