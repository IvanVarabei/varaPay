package com.epam.varapay.model.validator;

import com.epam.varapay.model.service.DataTransferMapKey;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class PaymentValidatorTest {
    private PaymentValidator paymentValidator = PaymentValidator.getInstance();
    private Map<String, String> properPaymentData = new HashMap<>();

    @BeforeClass
    private void setUp() {
        properPaymentData.put(DataTransferMapKey.AMOUNT, "56");
        properPaymentData.put(DataTransferMapKey.CARD_NUMBER, "1111 2222 3333 4444");
        properPaymentData.put(DataTransferMapKey.CVC, "123");
        properPaymentData.put(DataTransferMapKey.VALID_THRU, "2023-11");
    }

    @DataProvider(name = "amountValues")
    public Object[][] amountValues() {
        Map<String, String> spoiledDataNegative = new HashMap<>(properPaymentData);
        spoiledDataNegative.put(DataTransferMapKey.AMOUNT, "-0.01");

        Map<String, String> spoiledDataZero = new HashMap<>(properPaymentData);
        spoiledDataZero.put(DataTransferMapKey.AMOUNT, "0");

        Map<String, String> spoiledDataZeroDot = new HashMap<>(properPaymentData);
        spoiledDataZeroDot.put(DataTransferMapKey.AMOUNT, "0.00");

        Map<String, String> spoiledDataComma = new HashMap<>(properPaymentData);
        spoiledDataComma.put(DataTransferMapKey.AMOUNT, "0,01");

        Map<String, String> properDataBottomEdge = new HashMap<>(properPaymentData);
        properDataBottomEdge.put(DataTransferMapKey.AMOUNT, "0.01");

        Map<String, String> properDataCeilingEdge = new HashMap<>(properPaymentData);
        properDataCeilingEdge.put(DataTransferMapKey.AMOUNT, "999999999.99");

        Map<String, String> spoiledDataOverCeilingEdge = new HashMap<>(properPaymentData);
        spoiledDataOverCeilingEdge.put(DataTransferMapKey.AMOUNT, "1000000000.00");

        return new Object[][]{
                {spoiledDataNegative, false},
                {spoiledDataZero, false},
                {spoiledDataZeroDot, false},
                {spoiledDataComma, false},
                {properDataBottomEdge, true},
                {properDataCeilingEdge, true},
                {spoiledDataOverCeilingEdge, false},
        };
    }

    @Test(dataProvider = "amountValues")
    public void isValidPaymentCheckAmount(Map<String, String> paymentData, boolean expected) {
        boolean actual = paymentValidator.isValidPayment(paymentData);

        assertEquals(actual, expected);
    }

    @DataProvider(name = "cardNumberValues")
    public Object[][] cardNumberValues() {
        Map<String, String> spoiledDataLength15 = new HashMap<>(properPaymentData);
        spoiledDataLength15.put(DataTransferMapKey.CARD_NUMBER, "1111 2222 3333 444");

        Map<String, String> spoiledDataLength17 = new HashMap<>(properPaymentData);
        spoiledDataLength17.put(DataTransferMapKey.CARD_NUMBER, "1111 2222 3333 4444 5");

        Map<String, String> spoiledDataUnderscore = new HashMap<>(properPaymentData);
        spoiledDataUnderscore.put(DataTransferMapKey.CARD_NUMBER, "1111_2222_3333_4444");

        Map<String, String> spoiledDataNotNumber = new HashMap<>(properPaymentData);
        spoiledDataNotNumber.put(DataTransferMapKey.CARD_NUMBER, "aaaa bbbb cccc dddd");

        Map<String, String> properDataSpaces = new HashMap<>(properPaymentData);
        properDataSpaces.put(DataTransferMapKey.CARD_NUMBER, "  1 111 22 22 333 3 444 4  ");

        return new Object[][]{
                {spoiledDataLength15, false},
                {spoiledDataLength17, false},
                {spoiledDataUnderscore, false},
                {spoiledDataNotNumber, false},
                {properDataSpaces, true},
        };
    }

    @Test(dataProvider = "cardNumberValues")
    public void isValidPaymentCheckCardNumber(Map<String, String> paymentData, boolean expected) {
        boolean actual = paymentValidator.isValidPayment(paymentData);

        assertEquals(actual, expected);
    }

    @DataProvider(name = "cvcValues")
    public Object[][] cvcValues() {
        Map<String, String> spoiledDataLength2 = new HashMap<>(properPaymentData);
        spoiledDataLength2.put(DataTransferMapKey.CVC, "11");

        Map<String, String> spoiledDataLength4 = new HashMap<>(properPaymentData);
        spoiledDataLength4.put(DataTransferMapKey.CVC, "1111");

        Map<String, String> spoiledDataNotNumber = new HashMap<>(properPaymentData);
        spoiledDataNotNumber.put(DataTransferMapKey.CVC, "aaa");

        Map<String, String> spoiledDataSpaces = new HashMap<>(properPaymentData);
        spoiledDataSpaces.put(DataTransferMapKey.CVC, "1 2 3");

        return new Object[][]{
                {spoiledDataLength2, false},
                {spoiledDataLength4, false},
                {spoiledDataNotNumber, false},
                {spoiledDataSpaces, false},
                {properPaymentData, true},
        };
    }

    @Test(dataProvider = "cvcValues")
    public void isValidPaymentCheckCvc(Map<String, String> paymentData, boolean expected) {
        boolean actual = paymentValidator.isValidPayment(paymentData);

        assertEquals(actual, expected);
    }

    @DataProvider(name = "validThruValues")
    public Object[][] validThruValues() {
        Map<String, String> spoiledDataWithDay = new HashMap<>(properPaymentData);
        spoiledDataWithDay.put(DataTransferMapKey.VALID_THRU, "2023-11-11");

        Map<String, String> spoiledDataUnderBottomEdge = new HashMap<>(properPaymentData);
        spoiledDataUnderBottomEdge.put(DataTransferMapKey.VALID_THRU, "2019-12");

        Map<String, String> spoiledDataMonthThenYear = new HashMap<>(properPaymentData);
        spoiledDataMonthThenYear.put(DataTransferMapKey.VALID_THRU, "11-2021");

        Map<String, String> spoiledDataMonthHigherThan12 = new HashMap<>(properPaymentData);
        spoiledDataMonthHigherThan12.put(DataTransferMapKey.VALID_THRU, "2021-13");

        Map<String, String> spoiledDataMonth1 = new HashMap<>(properPaymentData);
        spoiledDataMonth1.put(DataTransferMapKey.VALID_THRU, "2019-1");

        Map<String, String> spoiledDataMonthZero = new HashMap<>(properPaymentData);
        spoiledDataMonthZero.put(DataTransferMapKey.VALID_THRU, "2019-0");

        Map<String, String> properDataBottomEdge = new HashMap<>(properPaymentData);
        properDataBottomEdge.put(DataTransferMapKey.VALID_THRU, "2020-01");

        Map<String, String> properDataCeilingEdge = new HashMap<>(properPaymentData);
        properDataCeilingEdge.put(DataTransferMapKey.VALID_THRU, "2029-12");

        Map<String, String> properDataOverCeilingEdge = new HashMap<>(properPaymentData);
        properDataOverCeilingEdge.put(DataTransferMapKey.VALID_THRU, "2030-01");

        return new Object[][]{
                {spoiledDataWithDay, false},
                {spoiledDataMonthThenYear, false},
                {spoiledDataMonthHigherThan12, false},
                {spoiledDataMonth1, false},
                {spoiledDataMonthZero, false},
                {spoiledDataUnderBottomEdge, false},
                {properDataBottomEdge, true},
                {properDataCeilingEdge, true},
                {properDataOverCeilingEdge, false},
        };
    }

    @Test(dataProvider = "validThruValues")
    public void isValidPaymentCheckValidThru(Map<String, String> paymentData, boolean expected) {
        boolean actual = paymentValidator.isValidPayment(paymentData);

        assertEquals(actual, expected);
    }
}
