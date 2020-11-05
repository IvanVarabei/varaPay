package com.epam.varapay.model.service;


import com.epam.varapay.model.service.impl.*;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final CardService cardService = new CardServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();
    private final AccountService accountService = new AccountServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final BidService bidService = new BidServiceImpl();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
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

    public BidService getBidService() {
        return bidService;
    }

    public AccountService getAccountService() {
        return accountService;
    }
}