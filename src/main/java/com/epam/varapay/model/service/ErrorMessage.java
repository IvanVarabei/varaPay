package com.epam.varapay.model.service;

public enum ErrorInfo {
    NAME,
    LENGTH,
    ALREADY_EXISTS,
    DIFFERENT_PASSWORDS,
    LOGIN,
    EMAIL,
    OLD_PASSWORD,
    TEMP_CODE,
    CVC,
    CARD_NUMBER,
    LOGIN_OCCUPIED,
    NOT_ENOUGH_BALANCE,
    SOURCE_ACCOUNT_BLOCKED,
    DESTINATION_ACCOUNT_BLOCKED,
    NOT_NUMBER,
    CAN_NOT_BE_EMPTY,
    CARD_DOES_NOT_EXISTS,
    AMOUNT,
    VALID_THRU,
    BALANCE_NOT_EMPTY,
    IN_PROGRESS_BIDS;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}

