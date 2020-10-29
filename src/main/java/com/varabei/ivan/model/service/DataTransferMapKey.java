package com.varabei.ivan.model.service;

public class DataTransferMapKey {
    public static final String COMMAND = "command";
    public static final String CURRENCY = "currency";
    public static final String PAGE = "page";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String REPEAT_PASSWORD = "repeatPassword";
    public static final String TEMP_CODE = "tempCode";
    public static final String LOCALE = "locale";
    public static final String QUERY = "query";

    //User
    public static final String USER_ID = "user_id";
    public static final String ROLE_NAME = "role_name";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String BIRTH = "birth";
    public static final String SECRET_WORD = "secret_word";

    //Account
    public static final String ACCOUNT_ID = "account_id";

    //Card
    public static final String CARD_ID = "card_id";
    public static final String NUMBER = "card_number";
    public static final String VALID_THRU = "valid_thru";
    public static final String CVC = "cvc";

    //Payment
    public static final String DESTINATION_CARD_NUMBER = "destination_card_number";
    public static final String AMOUNT = "amount";

    //Bid
    public static final String BID_ID = "bid_id";
    public static final String CLIENT_MESSAGE = "client_message";
    public static final String ADMIN_COMMENT = "admin_comment";

    private DataTransferMapKey() {
    }
}
