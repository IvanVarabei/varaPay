package com.epam.varapay.model.service.impl;


import com.epam.varapay.model.dao.BidDao;
import com.epam.varapay.model.dao.impl.BidDaoImpl;
import com.epam.varapay.model.exception.DaoException;
import com.epam.varapay.model.exception.ServiceException;
import org.mockito.InjectMocks;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.Mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class BidServiceImplTest {
    @Mock
    private BidDao bidDao = new BidDaoImpl();
    @InjectMocks
    private BidServiceImpl bidService = new BidServiceImpl();

    @BeforeClass
    private void setUp() throws DaoException {
        initMocks(this);
        when(bidDao.findAmountOfInProgressBids()).thenReturn(7l);
    }

    @DataProvider(name = "recordsPerPage")
    public static Object[][] recordsPerPage() {
        return new Object[][]{{3, 3}, {2, 4}};
    }
    @DataProvider(name = "limitPageIndexExpectedOffset")
    public static Object[][] limitPageIndexExpectedOffset() {
        return new Object[][]{{1, 1, 0}, {2, 4, 6}};
    }

    @Test(dataProvider = "recordsPerPage")
    public void findAmountOfPagesReturnAmountOfPages(int recordsPerPage, int expected) throws ServiceException {
        int actual = bidService.findAmountOfPages(recordsPerPage);

        assertEquals(actual, expected);
    }

    @Test(dataProvider = "limitPageIndexExpectedOffset")
    public void findInProgressBidsDaoRequest(int limit, int pageIndex, int expectedOffset)
            throws ServiceException, DaoException {
        bidService.findInProgressBids(limit, pageIndex);

        verify(bidDao).findInProgressBids(limit, expectedOffset);
    }
}
