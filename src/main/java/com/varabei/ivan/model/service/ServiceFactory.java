package com.varabei.ivan.model.service;


import com.varabei.ivan.model.service.impl.CardServiceImpl;
import com.varabei.ivan.model.service.impl.PaymentServiceImpl;
import com.varabei.ivan.model.service.impl.UserServiceImpl;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final UserService userService = new UserServiceImpl();
    private final CardService cardService = new CardServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();

    private ServiceFactory() {
    }

    public UserService getUserService() {
        return userService;
    }
    public CardService getCardService() {
        return cardService;
    }
    public PaymentService getPaymentService() {
        return paymentService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}