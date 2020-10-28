package com.varabei.ivan.validator;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.model.entity.name.CardField;
import com.varabei.ivan.model.entity.name.PaymentField;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PaymentValidator {
    private static final Pattern VALID_THRU_PATTERN = Pattern.compile("^(202\\d)-(1[0-2]|0[1-9])$");
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^\\d+\\.?\\d*$");
    private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^(\\s?\\d\\s?){16}$");
    private static final Pattern CVC_PATTERN = Pattern.compile("^\\d{3}$");

    public boolean isValidPayment(Map<String, String> paymentData) {
        Map<String, String> initialMap = new HashMap<>(paymentData);
        checkCvc(paymentData);
        checkDestinationCardNumber(paymentData);
        checkAmount(paymentData);
        checkValidThru(paymentData);
        return initialMap.equals(paymentData);
    }

    private void checkValidThru(Map<String, String> paymentData) {
        if (!VALID_THRU_PATTERN.matcher(paymentData.get(CardField.VALID_THRU)).find()) {
            paymentData.put(CardField.VALID_THRU, ErrorInfo.VALID_THRU.toString());
        }
    }

    private void checkAmount(Map<String, String> paymentData) {
        if (!AMOUNT_PATTERN.matcher(paymentData.get(PaymentField.AMOUNT)).find()) {
            paymentData.put(PaymentField.AMOUNT, ErrorInfo.AMOUNT.toString());
        }
    }

    private void checkDestinationCardNumber(Map<String, String> paymentData) {
        if (!CARD_NUMBER_PATTERN.matcher(paymentData.get(CardField.NUMBER)).find()) {
            paymentData.put(CardField.NUMBER, ErrorInfo.CARD_NUMBER.toString());
        }
    }

    private void checkCvc(Map<String, String> paymentData) {
        if (!CVC_PATTERN.matcher(paymentData.get(CardField.CVC)).find()) {
            paymentData.put(CardField.CVC, ErrorInfo.CVC.toString());
        }
    }
}
