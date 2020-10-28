package com.varabei.ivan.model.service;


import com.varabei.ivan.model.service.impl.*;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private final MailService mailService = new MailServiceImpl();
    private final UserService userService = new UserServiceImpl(mailService);
    private final CardService cardService = new CardServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl();
    private final AccountService accountService = new AccountServiceImpl();
    private final BidService bidService = new BidServiceImpl();
    private final CurrencyService currencyService = new CurrencyServiceImpl();

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

    public MailService getMailService() {
        return mailService;
    }

    public CurrencyService getCurrencyService() {
        return currencyService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }
}