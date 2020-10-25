package com.varabei.ivan.common;

public class ErrorInfo {
    public static final String WRONG_NAME = ">=3 and <=20 letter";
    public static final String WRONG_SECRET_WORD = "wrong secret word";
    public static final String WRONG_LENGTH = ">=3 and <=20";
    public static final String ALREADY_EXISTS = "such a value already exists";
    public static final String DIFFERENT_PASSWORDS = "passwords are different";
    public static final String WRONG_LOGIN = ">=3 and <=20 letter, number, _";
    public static final String WRONG_EMAIL = "wrong email format";
    public static final String WRONG_LOGIN_OR_PASSWORD = "wrong login or password";
    public static final String WRONG_OLD_PASSWORD = "wrong old password";
    public static final String WRONG_TEMP_CODE = "wrong temporary code";
    public static final String WRONG_CVC = "wrong cvc";
    public static final String WRONG_CARD_NUMBER = "wrong card number";
    public static final String LOGIN_TAKEN = "During your registration an another user`s took '%s' login. " +
            "Repeat registration from scratch.";
    public static final String EMAIL_DOES_NOT_EXISTS = "such an email does`t exists";
    public static final String NOT_ENOUGH_BALANCE = "not enough balance";
    public static final String SOURCE_ACCOUNT_BLOCKED = "source account is blocked";
    public static final String DESTINATION_ACCOUNT_BLOCKED = "destination account is blocked";
    public static final String NOT_NUMBER = "not a number";
    public static final String CAN_NOT_BE_EMPTY = "can`t be empty";
    public static final int SERVER_ERROR_CODE = 500;

    private ErrorInfo() {
    }
}