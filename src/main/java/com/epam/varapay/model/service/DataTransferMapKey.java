package com.epam.varapay.model.service;

public class DataTransferMapKey {
    //User
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String REPEAT_PASSWORD = "repeatPassword";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String SECRET_WORD = "secretWord";

    //Account
    public static final String ACCOUNT_ID = "accountId";

    //Card
    public static final String CARD_ID = "cardId";
    public static final String CARD_NUMBER = "cardNumber";
    public static final String VALID_THRU = "validThru";
    public static final String CVC = "cvc";

    //Payment
    public static final String AMOUNT = "amount";

    //Bid
    public static final String BID_ID = "bidId";
    public static final String CLIENT_MESSAGE = "clientMessage";
    public static final String CURRENCY = "currency";

    private DataTransferMapKey() {
    }
}
