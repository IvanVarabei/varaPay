package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.entity.name.UserField;
import com.varabei.ivan.model.service.MailService;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.util.CustomSecurity;
import com.varabei.ivan.validator.FormValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SignupCommand implements ActionCommand {
    private static MailService mailService = ServiceFactory.getInstance().getMailService();
    private static final String MAIL_SUBJECT_EMAIL_VERIFICATION = "Email verification";
    private static final String MAIL_CONTENT = "Hi! Your verification code is : %s. " +
            "Enter code into the form.";
    private static final int VERIFICATION_CODE_LENGTH = 4;

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.VERIFY_EMAIL);
        Map<String, String> errors = new HashMap<>();
        errors.put(UserField.LOGIN, req.getParameter(UserField.LOGIN));
        errors.put(UserField.PASSWORD, req.getParameter(UserField.PASSWORD));
        errors.put(RequestParam.REPEAT_PASSWORD, req.getParameter(RequestParam.REPEAT_PASSWORD));
        errors.put(UserField.FIRST_NAME, req.getParameter(UserField.FIRST_NAME));
        errors.put(UserField.LAST_NAME, req.getParameter(UserField.LAST_NAME));
        errors.put(UserField.EMAIL, req.getParameter(UserField.EMAIL));
        errors.put(UserField.BIRTH, req.getParameter(UserField.BIRTH));
        errors.put(UserField.SECRET_WORD, req.getParameter(UserField.SECRET_WORD));
        if (new FormValidator().isValidSignupData(errors)) {
            String tempCode = CustomSecurity.generateRandom(VERIFICATION_CODE_LENGTH);
            mailService.sendEmail(errors.get(UserField.EMAIL), MAIL_SUBJECT_EMAIL_VERIFICATION,
                    String.format(MAIL_CONTENT, tempCode));
            req.getSession().setAttribute(RequestParam.TEMP_CODE, tempCode);
            req.getSession().setAttribute(AttributeKey.USER,
                    new User(errors.get(UserField.LOGIN), errors.get(UserField.FIRST_NAME),
                            errors.get(UserField.LAST_NAME), errors.get(UserField.EMAIL),
                            LocalDate.parse(errors.get(UserField.BIRTH))));
            req.getSession().setAttribute(UserField.PASSWORD, errors.get(UserField.PASSWORD));
            req.getSession().setAttribute(UserField.SECRET_WORD, errors.get(UserField.SECRET_WORD));
        } else {
            req.setAttribute(AttributeKey.ERRORS, errors);
            router.setForward(JspPath.SIGNUP);
        }
        return router;
    }

//    @Override
//    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//        Router router = new Router(JspPath.VERIFY_EMAIL);
//        String login = req.getParameter(UserField.LOGIN);
//        String password = req.getParameter(UserField.PASSWORD);
//        String repeatPassword = req.getParameter(RequestParam.REPEAT_PASSWORD);
//        String firstName = req.getParameter(UserField.FIRST_NAME);
//        String lastName = req.getParameter(UserField.LAST_NAME);
//        String email = req.getParameter(UserField.EMAIL);
//        String birth = req.getParameter(UserField.BIRTH);
//        String secretWord = req.getParameter(UserField.SECRET_WORD);
//        Map<String, String> errors = new HashMap<>();
//        try {
//            checkLogin(login, errors);
//            checkEmail(email, errors);
//            checkName(firstName, errors, UserField.FIRST_NAME);
//            checkName(lastName, errors, UserField.LAST_NAME);
//            checkName(secretWord, errors, UserField.SECRET_WORD);
//            checkPasswords(password, repeatPassword, errors);
//            if (errors.isEmpty()) {
//                String tempCode = CustomSecurity.generateRandom(VERIFICATION_CODE_LENGTH);
//                mailService.sendEmail(email, MAIL_SUBJECT_EMAIL_VERIFICATION, String.format(MAIL_CONTENT, tempCode));
//                req.getSession().setAttribute(RequestParam.TEMP_CODE, tempCode);
//                req.getSession().setAttribute(AttributeKey.USER,
//                        new User(login, firstName, lastName, email, LocalDate.parse(birth)));
//                req.getSession().setAttribute(UserField.PASSWORD, password);
//                req.getSession().setAttribute(UserField.SECRET_WORD, secretWord);
//            } else {
//                req.setAttribute(AttributeKey.ERRORS, errors);
//                router.setForward(JspPath.SIGNUP);
//            }
//        } catch (ServiceException e) {
//            log.error(e);
//            router.setForward(JspPath.ERROR_500);
//        }
//        return router;
//    }
}
