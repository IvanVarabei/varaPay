package com.epam.varapay.model.validator;

import com.epam.varapay.model.service.DataTransferMapKey;
import com.epam.varapay.model.service.ErrorMessage;

import java.util.Map;
import java.util.regex.Pattern;

public class UserValidator {
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^\\w{3,20}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-zА-Яа-яёЁ]{3,20}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final int MIN_PASSWORD_LENGTH = 3;
    private static final int MAX_PASSWORD_LENGTH = 20;

    private UserValidator() {
    }

    private static class UserValidatorHolder {
        private static final UserValidator USER_VALIDATOR_INSTANCE = new UserValidator();
    }

    public static UserValidator getInstance() {
        return UserValidatorHolder.USER_VALIDATOR_INSTANCE;
    }

    public boolean isValidSignupData(Map<String, String> signupData) {
        boolean isValid = checkLogin(signupData);
        isValid &= checkEmail(signupData);
        isValid &= checkName(signupData.get(DataTransferMapKey.FIRST_NAME), signupData, DataTransferMapKey.FIRST_NAME);
        isValid &= checkName(signupData.get(DataTransferMapKey.LAST_NAME), signupData, DataTransferMapKey.LAST_NAME);
        isValid &= checkName(signupData.get(DataTransferMapKey.SECRET_WORD), signupData, DataTransferMapKey.SECRET_WORD);
        isValid &= checkPasswords(signupData);
        return isValid;
    }

    public boolean checkPasswords(Map<String, String> signupData) {
        String password = signupData.get(DataTransferMapKey.PASSWORD);
        String repeatPassword = signupData.get(DataTransferMapKey.REPEAT_PASSWORD);
        if (password == null || password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            signupData.put(DataTransferMapKey.PASSWORD, ErrorMessage.LENGTH.toString());
            return false;
        } else {
            if (!password.equals(repeatPassword)) {
                signupData.put(DataTransferMapKey.REPEAT_PASSWORD, ErrorMessage.DIFFERENT_PASSWORDS.toString());
                return false;
            }
        }
        return true;
    }

    private boolean checkName(String potentialName, Map<String, String> signupData, String errorKey) {
        if (potentialName == null || !NAME_PATTERN.matcher(potentialName).matches()) {
            signupData.put(errorKey, ErrorMessage.NAME.toString());
            return false;
        }
        return true;
    }

    private boolean checkEmail(Map<String, String> signupData) {
        String email = signupData.get(DataTransferMapKey.EMAIL);
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            signupData.put(DataTransferMapKey.EMAIL, ErrorMessage.EMAIL.toString());
            return false;
        }
        return true;
    }

    private boolean checkLogin(Map<String, String> signupData) {
        String login = signupData.get(DataTransferMapKey.LOGIN);
        if (login == null || !LOGIN_PATTERN.matcher(login).matches()) {
            signupData.put(DataTransferMapKey.LOGIN, ErrorMessage.LOGIN.toString());
            return false;
        }
        return true;
    }
}
