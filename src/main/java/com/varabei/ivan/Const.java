package com.varabei.ivan;

public class Const {
    private Const() {
    }

    public static class ErrorInfo {
        public static final String WRONG_NAME = ">=3 and <=20 letter";
        public static final String WRONG_LENGTH = ">=3 and <=20";
        public static final String ALREADY_EXISTS = "such a value already exists";
        public static final String DIFFERENT_PASSWORDS = "passwords are different";
        public static final String WRONG_DATE = "wrong date format";
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
        public static final String ID = "userId";
        public static final String ROLE_NAME = "roleName";
        public static final String LOGIN = "login";
        public static final String PASSWORD = "password";
        public static final String REPEAT_PASSWORD = "repeatPassword";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String EMAIL = "email";
        public static final String BIRTH = "birth";

        private UserField() {
        }
    }

    public static class CardField {
        public static final String ID = "cardId";
        public static final String NUMBER = "cardNumber";
        public static final String VALID_THRU = "validThru";
        public static final String CVC = "cvc";

        private CardField() {
        }
    }

    public static class AccountField {
        public static final String ID = "accountId";
        public static final String BALANCE = "balance";
        public static final String IS_ACTIVE = "isActive";

        private AccountField() {
        }
    }

    public static class PaymentField {
        public static final String ID = "paymentId";
        public static final String SOURCE_CARD_ID = "sourceCardId";
        public static final String DESTINATION_CARD_ID = "destinationCardId";
        public static final String AMOUNT = "amount";
        public static final String INSTANT = "paymentInstant";

        private PaymentField() {
        }
    }
}
