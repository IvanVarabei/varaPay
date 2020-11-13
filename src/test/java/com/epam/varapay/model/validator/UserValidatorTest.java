package com.epam.varapay.model.validator;

import com.epam.varapay.model.service.DataTransferMapKey;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class UserValidatorTest {
    private UserValidator userValidator = UserValidator.getInstance();
    private Map<String, String> properSignupData = new HashMap<>();

    @BeforeClass
    private void setUp() {
        properSignupData.put(DataTransferMapKey.LOGIN, "user1");
        properSignupData.put(DataTransferMapKey.EMAIL, "mail.name@gmail.com");
        properSignupData.put(DataTransferMapKey.FIRST_NAME, "Ivan");
        properSignupData.put(DataTransferMapKey.LAST_NAME, "Varabei");
        properSignupData.put(DataTransferMapKey.SECRET_WORD, "hello");
        properSignupData.put(DataTransferMapKey.PASSWORD, "qwerty");
        properSignupData.put(DataTransferMapKey.REPEAT_PASSWORD, "qwerty");
    }

    @DataProvider(name = "passwordValues")
    public Object[][] passwordValues() {
        Map<String, String> spoiledDataUppercase = new HashMap<>(properSignupData);
        spoiledDataUppercase.put(DataTransferMapKey.REPEAT_PASSWORD, "QWERTY");

        Map<String, String> spoiledDataSpaces = new HashMap<>(properSignupData);
        spoiledDataSpaces.put(DataTransferMapKey.REPEAT_PASSWORD, " qwerty ");

        Map<String, String> spoiledDataNullPassword = new HashMap<>(properSignupData);
        spoiledDataNullPassword.put(DataTransferMapKey.PASSWORD, null);

        Map<String, String> spoiledDataNullRepeatPassword = new HashMap<>(properSignupData);
        spoiledDataNullRepeatPassword.put(DataTransferMapKey.REPEAT_PASSWORD, null);

        Map<String, String> spoiledDataLessThan3 = new HashMap<>(properSignupData);
        spoiledDataLessThan3.put(DataTransferMapKey.PASSWORD, "12");
        spoiledDataLessThan3.put(DataTransferMapKey.REPEAT_PASSWORD, "12");

        Map<String, String> minLength = new HashMap<>(properSignupData);
        minLength.put(DataTransferMapKey.PASSWORD, "123");
        minLength.put(DataTransferMapKey.REPEAT_PASSWORD, "123");

        Map<String, String> maxLength = new HashMap<>(properSignupData);
        maxLength.put(DataTransferMapKey.PASSWORD, "12345678901234567890");
        maxLength.put(DataTransferMapKey.REPEAT_PASSWORD, "12345678901234567890");

        Map<String, String> spoiledDataMoreThan20 = new HashMap<>(properSignupData);
        spoiledDataMoreThan20.put(DataTransferMapKey.PASSWORD, "123456789012345678901");
        spoiledDataMoreThan20.put(DataTransferMapKey.REPEAT_PASSWORD, "123456789012345678901");

        return new Object[][]{
                {spoiledDataUppercase, false},
                {spoiledDataSpaces, false},
                {spoiledDataNullPassword, false},
                {spoiledDataNullRepeatPassword, false},
                {spoiledDataLessThan3, false},
                {spoiledDataMoreThan20, false},
                {minLength, true},
                {maxLength, true},
        };
    }

    @Test(dataProvider = "passwordValues")
    public void isValidSignupCheckPasswords(Map<String, String> paymentData, boolean expected) {
        boolean actual = userValidator.isValidSignupData(paymentData);

        assertEquals(actual, expected);
    }

    @DataProvider(name = "nameValues")
    public Object[][] nameValues() {
        Map<String, String> spoiledDataLessThan3 = new HashMap<>(properSignupData);
        spoiledDataLessThan3.put(DataTransferMapKey.FIRST_NAME, "ab");

        Map<String, String> minLength = new HashMap<>(properSignupData);
        minLength.put(DataTransferMapKey.FIRST_NAME, "abc");

        Map<String, String> maxLength = new HashMap<>(properSignupData);
        maxLength.put(DataTransferMapKey.FIRST_NAME, "abcdeabcdeabcdeabcde");

        Map<String, String> spoiledDataLongerThan20 = new HashMap<>(properSignupData);
        spoiledDataLongerThan20.put(DataTransferMapKey.FIRST_NAME, "abcdeabcdeabcdeabcdea");

        Map<String, String> spoiledDataNull = new HashMap<>(properSignupData);
        spoiledDataNull.put(DataTransferMapKey.FIRST_NAME, null);

        return new Object[][]{
                {spoiledDataLessThan3, false},
                {minLength, true},
                {maxLength, true},
                {spoiledDataLongerThan20, false},
                {spoiledDataNull, false},
        };
    }

    @Test(dataProvider = "nameValues")
    public void isValidSignupCheckName(Map<String, String> paymentData, boolean expected) {
        boolean actual = userValidator.isValidSignupData(paymentData);

        assertEquals(actual, expected);
    }

    @DataProvider(name = "mailValues")
    public Object[][] mailValues() {
        Map<String, String> spoiledDataNull = new HashMap<>(properSignupData);
        spoiledDataNull.put(DataTransferMapKey.EMAIL, null);

        Map<String, String> spoiledDataNoAt = new HashMap<>(properSignupData);
        spoiledDataNoAt.put(DataTransferMapKey.EMAIL, "garlic9991.gmail.com");

        return new Object[][]{
                {spoiledDataNull, false},
                {spoiledDataNoAt, false},
                {properSignupData, true},
        };
    }

    @Test(dataProvider = "mailValues")
    public void isValidSignupCheckEmail(Map<String, String> paymentData, boolean expected) {
        boolean actual = userValidator.isValidSignupData(paymentData);

        assertEquals(actual, expected);
    }

    @DataProvider(name = "loginValues")
    public Object[][] loginValues() {
        Map<String, String> spoiledDataNull = new HashMap<>(properSignupData);
        spoiledDataNull.put(DataTransferMapKey.LOGIN, null);

        Map<String, String> spoiledDataLessThan3 = new HashMap<>(properSignupData);
        spoiledDataLessThan3.put(DataTransferMapKey.LOGIN, "a1");

        Map<String, String> minLength = new HashMap<>(properSignupData);
        minLength.put(DataTransferMapKey.LOGIN, "ab1");

        Map<String, String> maxLength = new HashMap<>(properSignupData);
        maxLength.put(DataTransferMapKey.LOGIN, "abcdeabcdeabcdeabc12");

        Map<String, String> spoiledDataLongerThan20 = new HashMap<>(properSignupData);
        spoiledDataLongerThan20.put(DataTransferMapKey.LOGIN, "abcdeabcdeabcdeabc123");

        return new Object[][]{
                {spoiledDataNull, false},
                {spoiledDataLessThan3, false},
                {minLength, true},
                {maxLength, true},
                {spoiledDataLongerThan20, false},
        };
    }

    @Test(dataProvider = "loginValues")
    public void isValidSignupCheckLogin(Map<String, String> paymentData, boolean expected) {
        boolean actual = userValidator.isValidSignupData(paymentData);

        assertEquals(actual, expected);
    }
}
