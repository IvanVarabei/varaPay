package com.epam.varapay.model.validator;

import com.epam.varapay.model.service.DataTransferMapKey;
import com.epam.varapay.model.service.ErrorMessage;

import java.util.Map;
import java.util.regex.Pattern;

public class PaymentValidator {
    private static final Pattern VALID_THRU_PATTERN = Pattern.compile("^(202\\d)-(1[0-2]|0[1-9])$");
    private static final Pattern AMOUNT_PATTERN =
            Pattern.compile("^([1-9]\\d*\\.?\\d{0,2})|(0\\.((\\d[1-9])|([1-9]\\d)))$");
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^(\\s*\\d\\s*){16}$");
    private static final Pattern CVC_PATTERN = Pattern.compile("^\\d{3}$");

    public boolean isValidPayment(Map<String, String> paymentData) {
        boolean isValid = checkAmount(paymentData);
        isValid &= checkDestinationCardNumber(paymentData);
        isValid &= checkCvc(paymentData);
        isValid &= checkValidThru(paymentData);
        return isValid;
    }

    private boolean checkAmount(Map<String, String> paymentData) {
        if (!AMOUNT_PATTERN.matcher(paymentData.get(DataTransferMapKey.AMOUNT)).matches()) {
            paymentData.put(DataTransferMapKey.AMOUNT, ErrorMessage.AMOUNT.toString());
            return false;
        }
        return true;
    }

    private boolean checkDestinationCardNumber(Map<String, String> paymentData) {
        if (!CARD_NUMBER_PATTERN.matcher(paymentData.get(DataTransferMapKey.CARD_NUMBER)).matches()) {
            paymentData.put(DataTransferMapKey.CARD_NUMBER, ErrorMessage.CARD_NUMBER.toString());
            return false;
        }
        return true;
    }

    private boolean checkCvc(Map<String, String> paymentData) {
        if (!CVC_PATTERN.matcher(paymentData.get(DataTransferMapKey.CVC)).matches()) {
            paymentData.put(DataTransferMapKey.CVC, ErrorMessage.CVC.toString());
            return false;
        }
        return true;
    }

    private boolean checkValidThru(Map<String, String> paymentData) {
        if (!VALID_THRU_PATTERN.matcher(paymentData.get(DataTransferMapKey.VALID_THRU)).matches()) {
            paymentData.put(DataTransferMapKey.VALID_THRU, ErrorMessage.VALID_THRU.toString());
            return false;
        }
        return true;
    }
}
