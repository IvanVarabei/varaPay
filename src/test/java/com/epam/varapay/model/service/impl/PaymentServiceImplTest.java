package com.epam.varapay.model.service.impl;

import com.epam.varapay.exception.DaoException;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.dao.CardDao;
import com.epam.varapay.model.dao.PaymentDao;
import com.epam.varapay.model.service.PaymentService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class PaymentServiceImplTest {
    @Mock
    private PaymentDao paymentDao;
    @Mock
    private CardDao cardDao;
    @InjectMocks
    private PaymentService paymentService = new PaymentServiceImpl();

    @BeforeMethod
    public void setup(){
        initMocks(this);
    }

    @Test
    public void findPaymentByCardIdNoException() throws ServiceException, DaoException {
        paymentService.findPaymentsByCardId(1l, 3,2);

        verify(paymentDao).findPaymentsByCardId(1l, 3, 3);
    }

    @Test(expectedExceptions = ServiceException.class)
    public void findPaymentByCardIdDaoException() throws ServiceException, DaoException {
        doThrow(DaoException.class).when(paymentDao).findPaymentsByCardId(1l, 3, 3);

        paymentService.findPaymentsByCardId(1l, 3,2);
    }
}
