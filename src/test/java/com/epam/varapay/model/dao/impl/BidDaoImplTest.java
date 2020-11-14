package com.epam.varapay.model.dao.impl;

import com.epam.varapay.exception.DaoException;
import com.epam.varapay.model.entity.Bid;
import com.epam.varapay.model.entity.CustomCurrency;
import com.epam.varapay.model.pool.ConnectionPool;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

public class BidDaoImplTest {
    @Mock
    private ConnectionPool pool;
    @Mock
    private Connection connection;
    @Spy
    @InjectMocks
    private ProtectedAccessToPublicBidDao bidDaoSpy;
    private static List<Bid> executeQueryExpectedReturn = Collections.emptyList();

    static class ProtectedAccessToPublicBidDao extends BidDaoImpl {
        @Override
        public void init() {
        }

        @Override
        public void endTransaction(Connection connection) {
        }

        @Override
        public void executeUpdate(String query, Connection connection, Object... parameters) {
        }

        @Override
        public void executeUpdate(String query, Object... parameters) {
        }

        @Override
        public List<Bid> executeQuery(String query, Object... parameters) {
            return executeQueryExpectedReturn;
        }

        @Override
        public Optional<Long> findLong(String query, String columnLabel, Object... params) {
            return Optional.of(1L);
        }

        @Override
        public Optional<Long> findLong(String query, Connection connection, String columnLabel, Object... params) {
            return Optional.of(2L);
        }

        @Override
        public boolean findBoolean(String query, String columnLabel, Object... params) {
            return true;
        }
    }

    @BeforeMethod
    public void setup() {
        initMocks(this);
        when(pool.getConnection()).thenReturn(connection);
    }

    @Test
    public void placeTopUpBidSuccess() throws DaoException {
        bidDaoSpy.placeTopUpBid(1L, 2L, new BigDecimal(3), CustomCurrency.BITCOIN, "hi");

        verify(bidDaoSpy).executeUpdate("insert into bids (account_id, amount, " +
                        "amount_in_chosen_currency, currency_id, client_message, is_top_up) " +
                        "VALUES (?, ?, ?, ?, ?, true)",
                1L, 2L, new BigDecimal(3), CustomCurrency.BITCOIN.ordinal(), "hi");
    }

    @Test
    public void placeWithdrawBidSuccess() throws DaoException {
        bidDaoSpy.placeWithdrawBid(1L, 2L, new BigDecimal(3), CustomCurrency.BITCOIN, "hi");

        verify(bidDaoSpy).endTransaction(connection);
    }

    @Test(expectedExceptions = DaoException.class)
    public void placeWithdrawBidSqlException() throws DaoException {
        doThrow(SQLException.class).when(bidDaoSpy).endTransaction(connection);

        bidDaoSpy.placeWithdrawBid(1L, 2L, new BigDecimal(3), CustomCurrency.BITCOIN, "hi");
    }

    @Test
    public void findInProgressBids() throws DaoException {
        List<Bid> actual = bidDaoSpy.findInProgressBids(1, 2);

        assertSame(actual, executeQueryExpectedReturn);
    }

    @Test
    public void findByAccountId() throws DaoException {
        List<Bid> actual = bidDaoSpy.findByAccountId(1L, 2, 2);

        assertSame(actual, executeQueryExpectedReturn);
    }

    @Test
    public void findAmountOfInProgressBids() throws DaoException {
        long actual = bidDaoSpy.findAmountOfInProgressBids();
        long expected = 1;

        assertEquals(actual, expected);
    }

    @Test
    public void findAmountOfBidsByAccountId() throws DaoException {
        long actual = bidDaoSpy.findAmountOfBidsByAccountId(1L);
        long expected = 1;

        assertEquals(actual, expected);
    }

    @Test
    public void isPresentInProgressBids() throws DaoException {
        boolean actual = bidDaoSpy.isPresentInProgressBids(1L);

        assertTrue(actual);
    }

    @Test
    public void isTopUpBid() throws DaoException {
        boolean actual = bidDaoSpy.isTopUpBid(1L);

        assertTrue(actual);
    }

    @Test
    public void approveTopUpBidSuccess() throws DaoException {
        bidDaoSpy.approveTopUpBid(1L, "comment");

        verify(bidDaoSpy).endTransaction(connection);
    }

    @Test(expectedExceptions = DaoException.class)
    public void approveTopUpBidSqlException() throws DaoException {
        doThrow(SQLException.class).when(bidDaoSpy).endTransaction(connection);

        bidDaoSpy.approveTopUpBid(1L, "comment");
    }

    @Test
    public void approveWithdrawBid() throws DaoException {
        bidDaoSpy.approveWithdrawBid(1L, "comment");

        verify(bidDaoSpy).executeUpdate("update bids set bid_state_id = 2, " +
                "admin_comment = ? where bid_id = ?", "comment", 1L);
    }

    @Test
    public void rejectTopUpBidSuccess() throws DaoException {
        bidDaoSpy.rejectTopUpBid(1L, "comment");

        verify(bidDaoSpy).endTransaction(connection);
    }

    @Test(expectedExceptions = DaoException.class)
    public void rejectTopUpBidSqlException() throws DaoException {
        doThrow(SQLException.class).when(bidDaoSpy).endTransaction(connection);

        bidDaoSpy.rejectTopUpBid(1L, "comment");
    }

    @Test
    public void rejectWithdrawBidSuccess() throws DaoException {
        bidDaoSpy.rejectWithdrawBid(1L, "comment");

        verify(bidDaoSpy).endTransaction(connection);
    }

    @Test(expectedExceptions = DaoException.class)
    public void rejectWithdrawBidSqlException() throws DaoException {
        doThrow(SQLException.class).when(bidDaoSpy).endTransaction(connection);

        bidDaoSpy.rejectWithdrawBid(1L, "comment");
    }
}
