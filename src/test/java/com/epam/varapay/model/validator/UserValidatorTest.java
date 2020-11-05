package com.epam.varapay.model.validator;

import com.epam.varapay.model.service.DataTransferMapKey;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class UserValidatorTest {
    private UserValidator userValidator = new UserValidator();
    private Map<String, String> properSignupData = new HashMap<>();

    @BeforeClass
    private void setUp() {
        properSignupData.put(DataTransferMapKey.PASSWORD, "qwerty");
        properSignupData.put(DataTransferMapKey.REPEAT_PASSWORD, "qwerty");
    }

    @DataProvider(name = "amountValues")
    public Object[][] amountValues() {
        Map<String, String> spoiledDataUppercase = new HashMap<>(properSignupData);
        spoiledDataUppercase.put(DataTransferMapKey.REPEAT_PASSWORD, "QWERTY");

        Map<String, String> spoiledDataSpaces = new HashMap<>(properSignupData);
        spoiledDataSpaces.put(DataTransferMapKey.REPEAT_PASSWORD, " qwerty ");

        return new Object[][]{
                {spoiledDataUppercase, false},
                {spoiledDataSpaces, false},
                {properSignupData, true},

        };
    }

    @Test(dataProvider = "amountValues")
    public void isValidPaymentCheckAmount(Map<String, String> paymentData, boolean expected) {
        boolean actual = userValidator.checkPasswords(paymentData);

        assertEquals(actual, expected);
    }
}
