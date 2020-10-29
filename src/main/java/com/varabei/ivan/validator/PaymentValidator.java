package com.varabei.ivan.validator;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.model.entity.name.CardField;
import com.varabei.ivan.model.entity.name.PaymentField;

import java.util.Map;
import java.util.regex.Pattern;

public class PaymentValidator {
    private static final Pattern VALID_THRU_PATTERN = Pattern.compile("^(202\\d)-(1[0-2]|0[1-9])$");
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^\\d+\\.?\\d*$");
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^(\\s?\\d\\s?){16}$");
    private static final Pattern CVC_PATTERN = Pattern.compile("^\\d{3}$");

    public boolean isValidPayment(Map<String, String> paymentData) {
        return checkCvc(paymentData) &&
                checkDestinationCardNumber(paymentData) &&
                checkAmount(paymentData) &&
                checkValidThru(paymentData);
    }

    private boolean checkValidThru(Map<String, String> paymentData) {
        if (!VALID_THRU_PATTERN.matcher(paymentData.get(CardField.VALID_THRU)).find()) {
            paymentData.put(CardField.VALID_THRU, ErrorInfo.VALID_THRU.toString());
            return false;
        }
        return true;
    }

    private boolean checkAmount(Map<String, String> paymentData) {
        if (!AMOUNT_PATTERN.matcher(paymentData.get(PaymentField.AMOUNT)).find()) {
            paymentData.put(PaymentField.AMOUNT, ErrorInfo.AMOUNT.toString());
            return false;
        }
        return true;
    }

    private boolean checkDestinationCardNumber(Map<String, String> paymentData) {
        if (!CARD_NUMBER_PATTERN.matcher(paymentData.get(CardField.NUMBER)).find()) {
            paymentData.put(CardField.NUMBER, ErrorInfo.CARD_NUMBER.toString());
            return false;
        }
        return true;
    }

    private boolean checkCvc(Map<String, String> paymentData) {
        if (!CVC_PATTERN.matcher(paymentData.get(CardField.CVC)).find()) {
            paymentData.put(CardField.CVC, ErrorInfo.CVC.toString());
            return false;
        }
        return true;
    }
}
