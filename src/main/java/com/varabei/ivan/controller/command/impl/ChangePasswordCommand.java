package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.Error;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.RedirectPath;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.entity.name.UserField;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChangePasswordCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(ChangePasswordCommand.class);
    private static UserService userService = ServiceFactory.getInstance().getUserService();
    private static final int MIN_PASSWORD_LENGTH = 3;
    private static final int MAX_PASSWORD_LENGTH = 20;

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router();
        String oldPassword = req.getParameter(RequestParam.OLD_PASSWORD);
        String password = req.getParameter(UserField.PASSWORD);
        String repeatPassword = req.getParameter(RequestParam.REPEAT_PASSWORD);
        String login = req.getSession().getAttribute(UserField.LOGIN).toString();
        Map<String, String> errors = new HashMap<>();
        try {
            checkPasswords(password, repeatPassword, errors);
            if (userService.signIn(login, oldPassword).isEmpty()) {
                errors.put(RequestParam.OLD_PASSWORD, Error.OLD_PASSWORD.name().toLowerCase());
            }
            if (errors.isEmpty()) {
                User user = userService.findByLogin(login).get();
                userService.updatePassword(user.getEmail(), password);
                router.setRedirect(String.format(RedirectPath.CHANGING_PASSWORD, req.getContextPath()));
            } else {
                req.setAttribute(AttributeKey.ERRORS, errors);
                router.setForward(JspPath.CHANGE_PASSWORD);
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
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
}
