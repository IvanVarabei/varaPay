package com.epam.varapay.model.dao;

public class ColumnLabel {
    // General
    public static final String COUNT = "count";
    public static final String EXISTS = "exists";

    // User
    public static final String USER_ID = "user_id";
    public static final String ROLE_NAME = "role_name";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String SALT = "salt";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String BIRTH = "birth";

    // Account
    public static final String ACCOUNT_ID = "account_id";
    public static final String BALANCE = "balance";
    public static final String IS_ACTIVE = "is_active";

    // Card
    public static final String CARD_ID = "card_id";
    public static final String CARD_NUMBER = "card_number";
    public static final String VALID_THRU = "valid_thru";
    public static final String CVC = "cvc";

    // Payment
    public static final String PAYMENT_ID = "payment_id";
    public static final String SOURCE_CARD_ID = "source_card_id";
    public static final String DESTINATION_CARD_ID = "destination_card_id";
    public static final String PAYMENT_AMOUNT = "amount";
    public static final String INSTANT = "payment_instant";

    // Bid
    public static final String BID_ID = "bid_id";
    public static final String IS_TOP_UP = "is_top_up";
    public static final String STATE = "state";
    public static final String PLACING_DATE_TIME = "placing_date_time";
    public static final String BID_AMOUNT = "amount";
    public static final String CLIENT_MESSAGE = "client_message";
    public static final String ADMIN_COMMENT = "admin_comment";
    public static final String AMOUNT_IN_CHOSEN_CURRENCY = "amount_in_chosen_currency";
    public static final String CURRENCY_NAME = "currency_name";

    private ColumnLabel() {
    }
}
