package com.varabei.ivan.validator;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.model.entity.name.UserField;

import java.util.Map;
import java.util.regex.Pattern;

public class UserValidator {
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^\\w{3,20}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]{3,20}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final int MIN_PASSWORD_LENGTH = 3;
    private static final int MAX_PASSWORD_LENGTH = 20;

    public boolean isValidSignupData(Map<String, String> signupData) {
        return checkLogin(signupData) &&
                checkEmail(signupData) &&
                checkName(signupData.get(UserField.FIRST_NAME), signupData, UserField.FIRST_NAME) &&
                checkName(signupData.get(UserField.LAST_NAME), signupData, UserField.LAST_NAME) &&
                checkName(signupData.get(UserField.SECRET_WORD), signupData, UserField.SECRET_WORD) &&
                checkPasswords(signupData);
    }

    public boolean checkPasswords(Map<String, String> signupData) {
        String password = signupData.get(UserField.PASSWORD);
        String repeatPassword = signupData.get(RequestParam.REPEAT_PASSWORD);
        if (password.equals(repeatPassword)) {
            if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
                signupData.put(UserField.PASSWORD, ErrorInfo.LENGTH.name().toLowerCase());
                return false;
            }
            return true;
        }
        signupData.put(RequestParam.REPEAT_PASSWORD, ErrorInfo.DIFFERENT_PASSWORDS.name().toLowerCase());
        return false;
    }

    private boolean checkName(String potentialName, Map<String, String> signupData, String errorKey) {
        if (!NAME_PATTERN.matcher(potentialName).find()) {
            signupData.put(errorKey, ErrorInfo.NAME.name().toLowerCase());
            return false;
        }
        return true;
    }

    private boolean checkEmail(Map<String, String> signupData) {
        if (!EMAIL_PATTERN.matcher(signupData.get(UserField.EMAIL)).find()) {
            signupData.put(UserField.EMAIL, ErrorInfo.EMAIL.name().toLowerCase());
            return false;
        }
        return true;
    }

    private boolean checkLogin(Map<String, String> signupData) {
        if (!LOGIN_PATTERN.matcher(signupData.get(UserField.LOGIN)).find()) {
            signupData.put(UserField.LOGIN, ErrorInfo.LOGIN.name().toLowerCase());
            return false;
        }
        return true;
    }
}
