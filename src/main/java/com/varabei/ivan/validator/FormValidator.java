package com.varabei.ivan.validator;

import com.varabei.ivan.common.Error;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.model.entity.name.UserField;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FormValidator {
    private static final Logger log = LogManager.getLogger(FormValidator.class);
    private static UserService userService = ServiceFactory.getInstance().getUserService();
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^\\w{3,20}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]{3,20}$");//todo rus
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private static final int MIN_PASSWORD_LENGTH = 3;
    private static final int MAX_PASSWORD_LENGTH = 20;

    public boolean isValidSignupData(Map<String, String> errors) {
        try {
            Map<String, String> initialMap = new HashMap<>(errors);
            checkLogin(errors.get(UserField.LOGIN), errors);
            checkEmail(errors.get(UserField.EMAIL), errors);
            checkName(errors.get(UserField.FIRST_NAME), errors, UserField.FIRST_NAME);
            checkName(errors.get(UserField.LAST_NAME), errors, UserField.LAST_NAME);
            checkName(errors.get(UserField.SECRET_WORD), errors, UserField.SECRET_WORD);
            checkPasswords(errors.get(UserField.PASSWORD), errors.get(RequestParam.REPEAT_PASSWORD), errors);
            return initialMap.equals(errors);
        } catch (ServiceException e) {
            log.error(e);
            return false;
        }
    }

    private void checkPasswords(String password, String repeatPassword, Map<String, String> errors) {
        if (password.equals(repeatPassword)) {
            if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
                errors.put(UserField.PASSWORD, Error.LENGTH.name().toLowerCase());
            }
        } else {
            errors.put(RequestParam.REPEAT_PASSWORD, Error.DIFFERENT_PASSWORDS.name().toLowerCase());
        }
    }

    private void checkName(String potentialName, Map<String, String> errors, String errorKey) {
        if (!NAME_PATTERN.matcher(potentialName).find()) {
            errors.put(errorKey, Error.NAME.name().toLowerCase());
        }
    }

    private void checkEmail(String potentialEmail, Map<String, String> errors) throws ServiceException {
        if (EMAIL_PATTERN.matcher(potentialEmail).find()) {
            if (userService.findByEmail(potentialEmail).isPresent()) {
                errors.put(UserField.EMAIL, Error.ALREADY_EXISTS.name().toLowerCase());
            }
        } else {
            errors.put(UserField.EMAIL, Error.EMAIL.name().toLowerCase());
        }
    }

    private void checkLogin(String potentialLogin, Map<String, String> errors) throws ServiceException {
        if (LOGIN_PATTERN.matcher(potentialLogin).find()) {
            if (userService.findByLogin(potentialLogin).isPresent()) {
                errors.put(UserField.LOGIN, Error.ALREADY_EXISTS.name().toLowerCase());
            }
        } else {
            errors.put(UserField.LOGIN, Error.LOGIN.name().toLowerCase());
        }
    }
}
