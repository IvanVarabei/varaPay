package com.varabei.ivan.model.dao.impl;

import com.varabei.ivan.model.dao.BidDao;
import com.varabei.ivan.model.dao.GenericDao;
import com.varabei.ivan.model.dao.builder.impl.BidBoulder;
import com.varabei.ivan.model.entity.Bid;
import com.varabei.ivan.model.entity.name.BidField;
import com.varabei.ivan.model.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BidDaoImpl extends GenericDao<Bid> implements BidDao {
    private static final String FIND_BID_ACCOUNT_BY_BID_ID = "select account_id from bids where bid_id = ?";
    private static final String FIND_BID_AMOUNT_BY_ID = "select amount from bids where bid_id = ?";
    private static final String FIND_TOP_UP_BIDS = "select accounts.account_id, accounts.balance, accounts.is_active," +
            " bids.bid_id, bids.amount, bids.placing_date_time " +
            "from bids join accounts on bids.account_id = accounts.account_id " +
            "and is_top_up = true and bids.is_abandoned = false";
    private static final String FIND_WITHDRAW_BIDS = "select accounts.account_id, accounts.balance, accounts.is_active," +
            " bids.bid_id, bids.amount, bids.placing_date_time " +
            "from bids join accounts on bids.account_id = accounts.account_id " +
            "and is_top_up = false and bids.is_abandoned = false";
    private static final String FIND_IN_PROGRESS_BIDS =
            "select bid_id, bid_states.state,bids.amount,is_top_up, bids.client_message, bids.admin_comment,\n" +
            "       placing_date_time, accounts.account_id, accounts.account_id\n" +
            "       balance, is_active, users.user_id, users.login, password, salt,users.email,\n" +
            "       users.firstname, users.lastname, users.birth, roles.role_name from bids\n" +
            "       join bid_states on bids.bid_state_id = bid_states.bid_state_id\n" +
            "    join accounts on bids.account_id = accounts.account_id\n" +
            "        and bids.bid_state_id = 1\n" +
            "    join users on accounts.user_id = users.user_id\n" +
            "    join roles on users.role_id = roles.role_id order by placing_date_time desc limit ? offset ?";
    private static final String FIND_BY_ACCOUNT_ID =
            "select bid_id, bid_states.state,bids.amount, is_top_up, bids.client_message, bids.admin_comment,\n" +
                    "       placing_date_time, accounts.account_id, accounts.account_id\n" +
                    "       balance, is_active, users.user_id, users.login, password, salt,users.email,\n" +
                    "       users.firstname, users.lastname, users.birth, roles.role_name from bids\n" +
                    "       join bid_states on bids.bid_state_id = bid_states.bid_state_id\n" +
                    "    join accounts on bids.account_id = ? and  bids.account_id = accounts.account_id\n" +
                    "    join users on accounts.user_id = users.user_id\n" +
                    "    join roles on users.role_id = roles.role_id limit ? offset ?";
    private static final String FIND_BID_BY_ID = "select accounts.account_id, accounts.balance, accounts.is_active," +
            " bids.bid_id, bids.amount, bids.placing_date_time " +
            "from bids join accounts on bids.account_id = accounts.account_id and bid_id = ?";
    private static final String PLACE_TOP_UP_BID = "insert into bids (account_id, amount, is_top_up, client_message) " +
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
    private static final String FIND_NUMBER_OF_RECORDS = "select count(*) from bids where bid_state_id = 1";
    private static final String FIND_NUMBER_OF_RECORDS_BY_ACCOUNT_ID =
            "select count(*) from bids where account_id = ?";

    public BidDaoImpl() {
        super(new BidBoulder());
    }

    @Override
    public List<Bid> findInProgressBids(int limit, int offset) throws DaoException {
        return executeQuery(FIND_IN_PROGRESS_BIDS, limit, offset);
    }

    @Override
    public List<Bid> findByAccountId(Long accountId, int limit, int offset) throws DaoException {
        return executeQuery(FIND_BY_ACCOUNT_ID, accountId ,limit, offset);
    }

    @Override
    public void placeTopUpBid(Long accountId, Long amount, String message) throws DaoException {
        executeUpdate(PLACE_TOP_UP_BID, accountId, amount, message);
    }

    @Override
    public void placeWithdrawBid(Long accountId, Long amount, String message) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            startTransaction(connection);
            executeUpdate(PLACE_WITHDRAW_BID, connection, accountId, amount, message);
            executeUpdate(ADD_ACCOUNT_BALANCE, connection, -amount, accountId);
            endTransaction(connection);
        } catch (SQLException e) {
            cancelTransaction(connection);
            throw  new DaoException("can not get access to db", e);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void approveTopUpBid(Long topUpBidId) throws DaoException {
        Connection connection = pool.getConnection();
        try {
            startTransaction(connection);
            Long bidAmount = findLong(FIND_BID_AMOUNT_BY_ID, connection,
                    BidField.AMOUNT, topUpBidId).orElseThrow(DaoException::new);
            Long bidAccountId = findLong(FIND_BID_ACCOUNT_BY_BID_ID, connection,
                    BidField.ACCOUNT_ID, topUpBidId).orElseThrow(DaoException::new);
            executeUpdate(ADD_ACCOUNT_BALANCE, connection, bidAmount, bidAccountId);
            executeUpdate(SET_STATE_APPROVED, connection, topUpBidId);
            endTransaction(connection);
        } catch (SQLException | DaoException e) {
            DaoException daoException = e instanceof DaoException ? (DaoException) e : new DaoException(e);
            cancelTransaction(connection);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public void approveWithdrawBid(Long withdrawBidId) throws DaoException {
        executeUpdate(SET_STATE_APPROVED, withdrawBidId);
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
            DaoException daoException = new DaoException("can not get access to db", e);
            cancelTransaction(connection);
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
            DaoException daoException = new DaoException("can not get access to db", e);
            cancelTransaction(connection);
        } finally {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public Long findAmountOfInProgressBids() throws DaoException {
        return findLong(FIND_NUMBER_OF_RECORDS, "count").orElseThrow(DaoException::new);
    }

    @Override
    public Long findAmountOfBidsByAccountId(Long accountId) throws DaoException {
        return findLong(FIND_NUMBER_OF_RECORDS_BY_ACCOUNT_ID, "count", accountId).orElseThrow(DaoException::new);
    }
}
