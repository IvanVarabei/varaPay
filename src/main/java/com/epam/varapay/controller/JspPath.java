package com.epam.varapay.controller;

public class JspPath {
    // Admin
    public static final String RUN_ACCOUNTS = "/WEB-INF/pages/admin/runAccounts.jsp";
    public static final String RUN_BIDS = "/WEB-INF/pages/admin/runBids.jsp";

    // Authentication
    public static final String SIGNUP = "/WEB-INF/pages/authentication/signup.jsp";
    public static final String VERIFY_EMAIL = "/WEB-INF/pages/authentication/verifyEmail.jsp";
    public static final String RECOVER_PASSWORD = "/WEB-INF/pages/authentication/recoverPassword.jsp";
    public static final String LOGIN = "/WEB-INF/pages/authentication/login.jsp";

    // Client
    public static final String ACCOUNT = "/WEB-INF/pages/client/account.jsp";
    public static final String CARD = "/WEB-INF/pages/client/card.jsp";
    public static final String CHANGE_PASSWORD = "/WEB-INF/pages/client/changePassword.jsp";
    public static final String VERIFY_CREATE_CARD = "/WEB-INF/pages/client/verifyCreateCard.jsp";
    public static final String PROFILE = "/WEB-INF/pages/client/profile.jsp";
    public static final String TOP_UP_AMOUNT = "/WEB-INF/pages/client/topUpAmount.jsp";
    public static final String TOP_UP_MESSAGE = "/WEB-INF/pages/client/topUpMessage.jsp";
    public static final String WITHDRAW_AMOUNT = "/WEB-INF/pages/client/withdrawAmount.jsp";
    public static final String WITHDRAW_MESSAGE = "/WEB-INF/pages/client/withdrawMessage.jsp";

    // Include
    public static final String INCLUDE_ACCOUNTS = "/WEB-INF/pages/include/includeAccounts.jsp";
    public static final String INCLUDE_CARDS = "/WEB-INF/pages/include/includeCards.jsp";
    public static final String INCLUDE_CURRENCIES = "/WEB-INF/pages/include/includeCurrencies.jsp";

    // Error
    public static final String ERROR_500 = "/WEB-INF/pages/error/error500.jsp";
    public static final String ERROR_404 = "/WEB-INF/pages/error/error404.jsp";

    public static final String SUCCESS_PAGE = "/WEB-INF/pages/success.jsp";
    public static final String ACCESS_DENIED = "/WEB-INF/pages/accessDenied.jsp";
    public static final String WELCOME = "/WEB-INF/pages/welcome.jsp";

    private JspPath() {
    }
}
