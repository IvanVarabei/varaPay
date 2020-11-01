package com.varabei.ivan.controller;

public class RequestParam {
    // General
    public static final String COMMAND = "command";
    public static final String PAGE = "page";
    public static final String TEMP_CODE = "tempCode";
    public static final String LOCALE = "locale";
    public static final String QUERY = "query";

    // User
    public static final String USER_ID = "userId";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String REPEAT_PASSWORD = "repeatPassword";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String BIRTH = "birth";
    public static final String SECRET_WORD = "secretWord";

    // Account
    public static final String ACCOUNT_ID = "accountId";

    // Card
    public static final String CARD_ID = "cardId";
    public static final String CARD_NUMBER = "cardNumber";
    public static final String VALID_THRU = "validThru";
    public static final String CVC = "cvc";

    // Payment
    public static final String AMOUNT = "amount";

    // Bid
    public static final String BID_ID = "bidId";
    public static final String IS_TOP_UP = "isTopUp";
    public static final String CLIENT_MESSAGE = "clientMessage";
    public static final String ADMIN_COMMENT = "adminComment";
    public static final String CURRENCY = "currency";
    public static final String AMOUNT_IN_CHOSEN_CURRENCY = "amountInChosenCurrency";

    private RequestParam() {
    }
}