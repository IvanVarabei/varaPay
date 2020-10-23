package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.entity.name.UserField;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.MailService;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.UserService;
import com.varabei.ivan.util.CustomSecurity;
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

public class SignupCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(SignupCommand.class);
    private static UserService userService = ServiceFactory.getInstance().getUserService();
    private static MailService mailService = ServiceFactory.getInstance().getMailService();
    private static final String MAIL_SUBJECT_EMAIL_VERIFICATION = "Email verification";
    private static final String MAIL_CONTENT = "Hi! Your verification code is : %s. " +
            "Enter code into the form.";
    private static final int MIN_PASSWORD_LENGTH = 3;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final int VERIFICATION_CODE_LENGTH = 4;
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^\\w{3,20}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]{3,20}$");//todo rus
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter(UserField.LOGIN);
        String password = req.getParameter(UserField.PASSWORD);
        String repeatPassword = req.getParameter(RequestParam.REPEAT_PASSWORD);
        String firstName = req.getParameter(UserField.FIRST_NAME);
        String lastName = req.getParameter(UserField.LAST_NAME);
        String email = req.getParameter(UserField.EMAIL);
        String birth = req.getParameter(UserField.BIRTH);
        String secretWord = req.getParameter(UserField.SECRET_WORD);
        Map<String, String> errors = new HashMap<>();
        try {
            checkLogin(login, errors);
            checkEmail(email, errors);
            checkName(firstName, errors, UserField.FIRST_NAME);
            checkName(lastName, errors, UserField.LAST_NAME);
            checkPasswords(password, repeatPassword, errors);
            if (errors.isEmpty()) {
                String tempCode = CustomSecurity.generateRandom(VERIFICATION_CODE_LENGTH);
                mailService.sendEmail(email, MAIL_SUBJECT_EMAIL_VERIFICATION, String.format(MAIL_CONTENT, tempCode));
                req.getSession().setAttribute(RequestParam.TEMP_CODE, tempCode);
                req.getSession().setAttribute(AttributeKey.USER,
                        new User(login, firstName, lastName, email, LocalDate.parse(birth)));
                req.getSession().setAttribute(UserField.PASSWORD, password);
                req.getSession().setAttribute(UserField.SECRET_WORD, secretWord);
                req.getRequestDispatcher(JspPath.VERIFY_EMAIL).forward(req, resp);
            } else {
                req.setAttribute(AttributeKey.ERRORS, errors);
                req.getRequestDispatcher(JspPath.SIGNUP).forward(req, resp);
            }
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(ErrorInfo.SERVER_ERROR_CODE);
        }
    }

    private void checkPasswords(String password, String repeatPassword, Map<String, String> errors) {
        if (password.equals(repeatPassword)) {
            if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
                errors.put(UserField.PASSWORD, ErrorInfo.WRONG_LENGTH);
            }
        } else {
            errors.put(RequestParam.REPEAT_PASSWORD, ErrorInfo.DIFFERENT_PASSWORDS);
        }
    }

    private void checkName(String potentialName, Map<String, String> errors, String errorKey) {
        if (!NAME_PATTERN.matcher(potentialName).find()) {
            errors.put(errorKey, ErrorInfo.WRONG_NAME);
        }
    }

    private void checkEmail(String potentialEmail, Map<String, String> errors) throws ServiceException {
        if (EMAIL_PATTERN.matcher(potentialEmail).find()) {
            if (userService.findByEmail(potentialEmail).isPresent()) {
                errors.put(UserField.EMAIL, ErrorInfo.ALREADY_EXISTS);
            }
        } else {
            errors.put(UserField.EMAIL, ErrorInfo.WRONG_EMAIL);
        }
    }

    private void checkLogin(String potentialLogin, Map<String, String> errors) throws ServiceException {
        if (LOGIN_PATTERN.matcher(potentialLogin).find()) {
            if (userService.findByLogin(potentialLogin).isPresent()) {
                errors.put(UserField.LOGIN, "already_exists");
            }
        } else {
            errors.put(UserField.LOGIN, "login_error");
        }
    }
}
