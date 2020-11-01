package com.varabei.ivan.model.validator;

import com.varabei.ivan.model.service.DataTransferMapKey;
import com.varabei.ivan.model.service.ErrorInfo;

import java.util.Map;
import java.util.regex.Pattern;

public class PaymentValidator {
    private static final Pattern VALID_THRU_PATTERN = Pattern.compile("^(202\\d)-(1[0-2]|0[1-9])$");
    private static final Pattern AMOUNT_PATTERN =
            Pattern.compile("^([1-9]\\d*\\.?\\d{0,2})|(0\\.((\\d[1-9])|([1-9]\\d)))$");
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^(\\s?\\d\\s?){16}$");
    private static final Pattern CVC_PATTERN = Pattern.compile("^\\d{3}$");

    public boolean isValidPayment(Map<String, String> paymentData) {
        boolean isValid = checkCvc(paymentData);
        isValid &= checkDestinationCardNumber(paymentData);
        isValid &= checkAmount(paymentData);
        isValid &= checkValidThru(paymentData);
        return isValid;
    }

    private boolean checkValidThru(Map<String, String> paymentData) {
        if (!VALID_THRU_PATTERN.matcher(paymentData.get(DataTransferMapKey.VALID_THRU)).find()) {
            paymentData.put(DataTransferMapKey.VALID_THRU, ErrorInfo.VALID_THRU.toString());
            return false;
        }
        return true;
    }

    private boolean checkAmount(Map<String, String> paymentData) {
        if (!AMOUNT_PATTERN.matcher(paymentData.get(DataTransferMapKey.AMOUNT)).find()) {
            paymentData.put(DataTransferMapKey.AMOUNT, ErrorInfo.AMOUNT.toString());
            return false;
        }
        return true;
    }

    private boolean checkDestinationCardNumber(Map<String, String> paymentData) {
        if (!CARD_NUMBER_PATTERN.matcher(paymentData.get(DataTransferMapKey.CARD_NUMBER)).find()) {
            paymentData.put(DataTransferMapKey.CARD_NUMBER, ErrorInfo.CARD_NUMBER.toString());
            return false;
        }
        return true;
    }

    private boolean checkCvc(Map<String, String> paymentData) {
        if (!CVC_PATTERN.matcher(paymentData.get(DataTransferMapKey.CVC)).find()) {
            paymentData.put(DataTransferMapKey.CVC, ErrorInfo.CVC.toString());
            return false;
        }
        return true;
    }
}
