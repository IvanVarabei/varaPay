package com.epam.varapay.model.service.impl;

import com.epam.varapay.exception.DaoException;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.dao.CardDao;
import com.epam.varapay.model.dao.PaymentDao;
import com.epam.varapay.model.entity.Account;
import com.epam.varapay.model.entity.Card;
import com.epam.varapay.model.service.DataTransferMapKey;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PaymentServiceImplTest {
    @Mock
    private PaymentDao paymentDao;
    @Mock
    private CardDao cardDao;
    @Mock
    private Card sourceCard;
    @Mock
    private Card destinationCard;
    @Mock
    private Account sourceAccount;
    @Mock
    private Account destinationAccount;
    @InjectMocks
    private PaymentServiceImplConnectionPoolInitializationPrevented paymentService;
    private Map<String, String> paymentData = new HashMap<>();

    static class PaymentServiceImplConnectionPoolInitializationPrevented extends PaymentServiceImpl{
        @Override
        public void init(){
        }
    }

    @BeforeMethod
    public void setup() throws DaoException {
        initMocks(this);
        paymentData.put(DataTransferMapKey.CARD_ID, "1");
        paymentData.put(DataTransferMapKey.AMOUNT, "56");
        paymentData.put(DataTransferMapKey.CARD_NUMBER, "1111 2222 3333 4444");
        paymentData.put(DataTransferMapKey.CVC, "123");
        paymentData.put(DataTransferMapKey.VALID_THRU, "2023-11");

        when(paymentDao.findAmountOfRecordsByCardId(1L)).thenReturn(7L);
        when(cardDao.findById(1L)).thenReturn(Optional.of(sourceCard));
        when(cardDao.findByCardNumberAndValidThru("1111222233334444", YearMonth.parse("2023-11")))
                .thenReturn(Optional.of(destinationCard));

        when(sourceCard.getCvc()).thenReturn("123");
        when(sourceCard.getAccount()).thenReturn(sourceAccount);
        when(sourceAccount.getBalance()).thenReturn(new BigDecimal(100));
        when(sourceAccount.isActive()).thenReturn(true);

        when(destinationCard.getAccount()).thenReturn(destinationAccount);
        when(destinationAccount.isActive()).thenReturn(true);
    }

    @Test
    public void findPaymentByCardIdNoException() throws ServiceException, DaoException {
        paymentService.findPaymentsByCardId(1L, 3, 2);

        verify(paymentDao).findPaymentsByCardId(1L, 3, 3);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findPaymentByCardIdServiceException() throws ServiceException, DaoException {
        doThrow(DaoException.class).when(paymentDao).findPaymentsByCardId(1L, 3, 3);

        paymentService.findPaymentsByCardId(1L, 3, 2);
    }

    @Test
    public void findAmountOfPagesByCardId() throws ServiceException {
        int actual = paymentService.findAmountOfPagesByCardId(1L, 3);
        int expected = 3;

        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findAmountOfPagesByCardIdServiceException() throws DaoException, ServiceException {
        doThrow(DaoException.class).when(paymentDao).findAmountOfRecordsByCardId(1L);

        paymentService.findAmountOfPagesByCardId(1L, 3);
    }

    @Test
    public void makePaymentSuccess() throws ServiceException {
        boolean actual = paymentService.makePayment(paymentData);

        assertTrue(actual);
    }

    @Test
    public void makePaymentNotEnoughBalance() throws ServiceException {
        paymentData.put(DataTransferMapKey.AMOUNT, "516");
        paymentData.put(DataTransferMapKey.CVC, "516");
        when(sourceAccount.isActive()).thenReturn(false);
        when(destinationAccount.isActive()).thenReturn(false);

        boolean actual = paymentService.makePayment(paymentData);

        assertFalse(actual);
    }

    @Test
    public void makePaymentDestinationCardNotFound() throws ServiceException, DaoException {
        when(cardDao.findByCardNumberAndValidThru("1111222233334444", YearMonth.parse("2023-11")))
                .thenReturn(Optional.empty());

        boolean actual = paymentService.makePayment(paymentData);

        assertFalse(actual);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void makePaymentServiceException() throws ServiceException, DaoException {
        doThrow(DaoException.class).when(paymentDao)
                .makePayment(1L, "1111222233334444", 5600L);

        paymentService.makePayment(paymentData);
    }
}
