package com.varabei.ivan.model.service;


import com.varabei.ivan.model.service.impl.*;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final UserService userService = new UserServiceImpl();
    private final CardService cardService = new CardServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();
    private final AccountService accountService = new AccountServiceImpl();
    private final BidService bidService = new BidServiceImpl();

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

    public BidService getToUpBidService() {
        return bidService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}