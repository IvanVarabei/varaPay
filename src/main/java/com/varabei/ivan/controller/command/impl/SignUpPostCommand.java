package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpPostCommand implements ActionCommand {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final Logger log = LogManager.getLogger(SignUpPostCommand.class);
    private static final String FORWARD_SIGNUP_GET = "/mainServlet?command=signup_get";
    private static final int MIN_PASSWORD_LENGTH = 3;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^\\w{3,20}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]{3,20}$");//todo rus
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter(Const.UserField.LOGIN);
        String password = req.getParameter(Const.UserField.PASSWORD);
        String repeatPassword = req.getParameter(Const.UserField.REPEAT_PASSWORD);
        String firstName = req.getParameter(Const.UserField.FIRST_NAME);
        String lastName = req.getParameter(Const.UserField.LAST_NAME);
        String email = req.getParameter(Const.UserField.EMAIL);
        String birth = req.getParameter(Const.UserField.BIRTH);
        Map<String, String> errors = new HashMap<>();
        try {
            checkLogin(login, errors);
            checkEmail(email, errors);
            checkName(firstName, errors, Const.UserField.FIRST_NAME);
            checkName(lastName, errors, Const.UserField.LAST_NAME);
            checkPasswords(password, repeatPassword, errors);
            if (errors.isEmpty()) {
                userService.signUp(new User(login, password, firstName, lastName, email, LocalDate.parse(birth)));
                resp.sendRedirect(req.getContextPath());
            } else {
                req.setAttribute(Const.AttributeKey.ERRORS, errors);
                req.getRequestDispatcher(FORWARD_SIGNUP_GET).forward(req, resp);
            }
        } catch (ServiceException e) {
            log.error(e);
        }
    }

    private void checkPasswords(String password, String repeatPassword, Map<String, String> errors) {
        if (password.equals(repeatPassword)) {
            if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
                errors.put(Const.UserField.PASSWORD, Const.ErrorInfo.WRONG_LENGTH);
            }
        } else {
            errors.put(Const.UserField.REPEAT_PASSWORD, Const.ErrorInfo.DIFFERENT_PASSWORDS);
        }
    }

    private void checkName(String potentialName, Map<String, String> errors, String errorKey) {
        if (!NAME_PATTERN.matcher(potentialName).find()) {
            errors.put(errorKey, Const.ErrorInfo.WRONG_NAME);
        }
    }

    private void checkEmail(String potentialEmail, Map<String, String> errors) throws ServiceException {
        if (EMAIL_PATTERN.matcher(potentialEmail).find()) {
            if (userService.findByEmail(potentialEmail).isPresent()) {
                errors.put(Const.UserField.EMAIL, Const.ErrorInfo.ALREADY_EXISTS);
            }
        } else {
            errors.put(Const.UserField.EMAIL, Const.ErrorInfo.WRONG_EMAIL);
        }
    }

    private void checkLogin(String potentialLogin, Map<String, String> errors) throws ServiceException {
        if (LOGIN_PATTERN.matcher(potentialLogin).find()) {
            if (userService.findByLogin(potentialLogin).isPresent()) {
                errors.put(Const.UserField.LOGIN, Const.ErrorInfo.ALREADY_EXISTS);
            }
        } else {
            errors.put(Const.UserField.LOGIN, Const.ErrorInfo.WRONG_LOGIN);
        }
    }
}