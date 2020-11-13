package com.epam.varapay.model.dao.impl;

import com.epam.varapay.model.dao.BidDao;
import com.epam.varapay.model.dao.ColumnLabel;
import com.epam.varapay.model.dao.GenericDao;
import com.epam.varapay.model.dao.builder.impl.BidBoulder;
import com.epam.varapay.model.entity.Bid;
import com.epam.varapay.model.entity.CustomCurrency;
import com.epam.varapay.exception.DaoException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BidDaoImpl extends GenericDao<Bid> implements BidDao {
    private static final String FIND_BID_ACCOUNT_BY_BID_ID = "select account_id from bids where bid_id = ?";
    private static final String FIND_BID_AMOUNT_BY_ID = "select amount from bids where bid_id = ?";
    private static final String FIND_IN_PROGRESS_BIDS =
            "select bid_id, state, bids.amount, amount_in_chosen_currency, currency_name, is_top_up, client_message, " +
                    "admin_comment, placing_date_time, accounts.account_id, accounts.account_id\n" +
                    "       balance, is_active, users.user_id, users.login, password, salt,users.email,\n" +
                    "       users.firstname, users.lastname, users.birth, roles.role_name from bids\n" +
                    "       join bid_states on bids.bid_state_id = bid_states.bid_state_id\n" +
                    "    join accounts on bids.account_id = accounts.account_id\n" +
                    "        and bids.bid_state_id = 1\n" +
                    "    join users on accounts.user_id = users.user_id\n " +
                    " join currencies on bids.currency_id = currencies.currency_id " +
                    "    join roles on users.role_id = roles.role_id order by placing_date_time desc limit ? offset ?";
    private static final String FIND_BY_ACCOUNT_ID =
            "select bid_id, state, bids.amount, amount_in_chosen_currency, currency_name, is_top_up, client_message, " +
                    "admin_comment, placing_date_time, accounts.account_id, accounts.account_id\n" +
                    "       balance, is_active, users.user_id, users.login, password, salt,users.email,\n" +
                    "       users.firstname, users.lastname, users.birth, roles.role_name from bids\n" +
                    "       join bid_states on bids.bid_state_id = bid_states.bid_state_id\n" +
                    "    join accounts on bids.account_id = ? and  bids.account_id = accounts.account_id\n" +
                    "    join users on accounts.user_id = users.user_id\n" +
                    " join currencies on bids.currency_id = currencies.currency_id " +
                    "    join roles on users.role_id = roles.role_id order by placing_date_time desc limit ? offset ?";
    private static final String PLACE_TOP_UP_BID = "insert into bids (account_id, amount, amount_in_chosen_currency," +
            " currency_id, client_message, is_top_up) VALUES (?, ?, ?, ?, ?, true)";
    private static final String PLACE_WITHDRAW_BID = "insert into bids (account_id, amount, " +
            "amount_in_chosen_currency, currency_id, client_message, is_top_up) VALUES (?, ?, ?, ?, ?, false)";
    private static final String ADD_ACCOUNT_BALANCE = "update accounts set balance = balance + ? where account_id = ?";
    private static final String UPDATE_ADMIN_COMMENT = "update bids set admin_comment = ? where bid_id = ?";
    private static final String SET_STATE_REJECTED = "update bids set bid_state_id = 3 where bid_id = ?";
    private static final String SET_ADMIN_COMMENT_AND_STATE_APPROVED = "update bids set bid_state_id = 2, " +
            "admin_comment = ? where bid_id = ?";
    private static final String RECOVER_ACCOUNT_BALANCE_CONSIDERING_WITHDRAW_BID_ID =
            "update accounts set balance = balance + (select amount from bids where bid_id = ?) \n" +
                    "where account_id = (select account_id from bids where bid_id = ?)";
    private static final String FIND_NUMBER_OF_RECORDS = "select count(*) from bids where bid_state_id = 1";
    private static final String FIND_NUMBER_OF_RECORDS_BY_ACCOUNT_ID =
            "select count(*) from bids where account_id = ?";
    private static final String IS_PRESENT_IN_PROGRESS_BID_BY_ACCOUNT_ID = "select exists(select 1 from bids " +
            "where bid_state_id = 1 and account_id = ?)";
    private static final String IS_TOP_UP_BID = "select is_top_up from bids where bid_id = ?";

    public BidDaoImpl() {
        super(new BidBoulder());
    }

    @Override
    public void placeTopUpBid(Long accountId, Long amount, BigDecimal amountInChosenCurrency,
                              CustomCurrency currency, String message) throws DaoException {
        executeUpdate(PLACE_TOP_UP_BID, accountId, amount, amountInChosenCurrency, currency.ordinal(), message);
    }

    @Override
    public void placeWithdrawBid(Long accountId, Long amount, BigDecimal amountInChosenCurrency,
                                 CustomCurrency currency, String message) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            startTransaction(connection);
            executeUpdate(PLACE_WITHDRAW_BID, accountId, amount, amountInChosenCurrency, currency.ordinal(), message);
            executeUpdate(ADD_ACCOUNT_BALANCE, connection, -amount, accountId);
            endTransaction(connection);
        } catch (SQLException e) {
            cancelTransaction(connection);
            throw new DaoException("can not get access to db", e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public List<Bid> findInProgressBids(int limit, int offset) throws DaoException {
        return executeQuery(FIND_IN_PROGRESS_BIDS, limit, offset);
    }

    @Override
    public List<Bid> findByAccountId(Long accountId, int limit, int offset) throws DaoException {
        return executeQuery(FIND_BY_ACCOUNT_ID, accountId, limit, offset);
    }

    @Override
    public Long findAmountOfInProgressBids() throws DaoException {
        return findLong(FIND_NUMBER_OF_RECORDS, ColumnLabel.COUNT).orElseThrow(DaoException::new);
    }

    @Override
    public Long findAmountOfBidsByAccountId(Long accountId) throws DaoException {
        return findLong(FIND_NUMBER_OF_RECORDS_BY_ACCOUNT_ID, ColumnLabel.COUNT, accountId)
                .orElseThrow(DaoException::new);
    }

    @Override
    public boolean isPresentInProgressBids(Long accountId) throws DaoException {
        return findBoolean(IS_PRESENT_IN_PROGRESS_BID_BY_ACCOUNT_ID, ColumnLabel.EXISTS, accountId);
    }

    @Override
    public boolean isTopUpBid(Long bidId) throws DaoException {
        return findBoolean(IS_TOP_UP_BID, ColumnLabel.IS_TOP_UP, bidId);
    }

    @Override
    public void approveTopUpBid(Long topUpBidId, String adminComment) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            startTransaction(connection);
            Long bidAmount = findLong(FIND_BID_AMOUNT_BY_ID, connection,
                    ColumnLabel.BID_AMOUNT, topUpBidId).orElseThrow(DaoException::new);
            Long bidAccountId = findLong(FIND_BID_ACCOUNT_BY_BID_ID, connection,
                    ColumnLabel.ACCOUNT_ID, topUpBidId).orElseThrow(DaoException::new);
            executeUpdate(ADD_ACCOUNT_BALANCE, connection, bidAmount, bidAccountId);
            executeUpdate(SET_ADMIN_COMMENT_AND_STATE_APPROVED, connection, adminComment, topUpBidId);
            endTransaction(connection);
        } catch (SQLException | DaoException e) {
            cancelTransaction(connection);
            throw e instanceof DaoException ? (DaoException) e : new DaoException(e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void approveWithdrawBid(Long withdrawBidId, String adminComment) throws DaoException {
        executeUpdate(SET_ADMIN_COMMENT_AND_STATE_APPROVED,adminComment, withdrawBidId);
    }

    @Override
    public void rejectTopUpBid(Long topUpBidId, String adminComment) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            startTransaction(connection);
            executeUpdate(UPDATE_ADMIN_COMMENT, connection, adminComment, topUpBidId);
            executeUpdate(SET_STATE_REJECTED, connection, topUpBidId);
            endTransaction(connection);
        } catch (SQLException e) {
            cancelTransaction(connection);
            throw new DaoException("can not get access to db", e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void rejectWithdrawBid(Long withdrawBidId, String adminComment) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            startTransaction(connection);
            executeUpdate(UPDATE_ADMIN_COMMENT, connection, adminComment, withdrawBidId);
            executeUpdate(RECOVER_ACCOUNT_BALANCE_CONSIDERING_WITHDRAW_BID_ID, connection, withdrawBidId, withdrawBidId);
            executeUpdate(SET_STATE_REJECTED, connection, withdrawBidId);
            endTransaction(connection);
        } catch (SQLException e) {
            cancelTransaction(connection);
            throw new DaoException("can not get access to db", e);
        } finally {
            pool.releaseConnection(connection);
        }
    }
}
