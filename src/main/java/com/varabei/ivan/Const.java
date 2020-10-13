package com.varabei.ivan;

public class Const {
    private Const() {
    }

    public static class ErrorInfo {
        public static final String WRONG_NAME = ">=3 and <=20 letter";
        public static final String WRONG_LENGTH = ">=3 and <=20";
        public static final String ALREADY_EXISTS = "such a value already exists";
        public static final String DIFFERENT_PASSWORDS = "passwords are different";
        public static final String WRONG_LOGIN = ">=3 and <=20 letter, number, _";
        public static final String WRONG_EMAIL = "wrong email format";
        public static final String WRONG_LOGIN_OR_PASSWORD = "wrong login or password";

        private ErrorInfo() {
        }
    }

    public static class RequestParam {
        public static final String COMMAND = "command";

        private RequestParam() {
        }
    }

    public static class AttributeKey {
        public static final String USER_AMOUNT = "userAmount";
        public static final String PAYMENT_AMOUNT = "paymentAmount";
        public static final String ERRORS = "errors";
        public static final String ERROR = "error";
        public static final String USER = "user";

        private AttributeKey() {
        }
    }

    public static class UserField {
        public static final String ID = "user_id";
        public static final String ROLE_NAME = "role_name";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String REPEAT_PASSWORD = "repeat_password";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String EMAIL = "email";
        public static final String BIRTH = "birth";

        private UserField() {
        }
    }

    public static class CardField {
        public static final String ID = "card_id";
        public static final String NUMBER = "card_number";
        public static final String VALID_THRU = "valid_thru";
        public static final String CVC = "cvc";

        private CardField() {
        }
    }

    public static class AccountField {
        public static final String ID = "account_id";
        public static final String BALANCE = "balance";
        public static final String IS_ACTIVE = "is_active";

        private AccountField() {
        }
    }

    public static class PaymentField {
        public static final String ID = "payment_id";
        public static final String SOURCE_CARD_ID = "source_card_id";
        public static final String DESTINATION_CARD_ID = "destination_card_id";
        public static final String AMOUNT = "amount";
        public static final String INSTANT = "payment_instant";

        private PaymentField() {
        }
    }

    public static class BidField {
        public static final String ID = "bid_id";
        public static final String PLACING_DATE_TIME = "placing_date_time";

        private BidField() {
        }
    }
}
