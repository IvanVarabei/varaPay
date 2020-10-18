package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
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
    private static final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final String JSP_CHANGE_PASSWORD = "/WEB-INF/pages/changePassword.jsp";
    private static final String REDIRECT_AFTER_CHANGING_PASSWORD = "%s/mainServlet?command=profile_get";
    private static final int MIN_PASSWORD_LENGTH = 3;
    private static final int MAX_PASSWORD_LENGTH = 20;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String oldPassword = req.getParameter(Const.RequestParam.OLD_PASSWORD);
        String password = req.getParameter(Const.UserField.PASSWORD);
        String repeatPassword = req.getParameter(Const.RequestParam.REPEAT_PASSWORD);
        Long userId = Long.parseLong(req.getSession().getAttribute(Const.UserField.ID).toString());
        Map<String, String> errors = new HashMap<>();
        try {
            checkPasswords(password, repeatPassword, errors);
            if (!userService.checkPresenceByIdPassword(userId, oldPassword)) {
                errors.put(Const.RequestParam.OLD_PASSWORD, Const.ErrorInfo.WRONG_OLD_PASSWORD);
            }
            if (errors.isEmpty()) {
                userService.updatePassword(userId, password);
                resp.sendRedirect(String.format(REDIRECT_AFTER_CHANGING_PASSWORD, req.getContextPath()));
            } else {
                req.setAttribute(Const.AttributeKey.ERRORS, errors);
                req.getRequestDispatcher(JSP_CHANGE_PASSWORD).forward(req, resp);
            }
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(Const.ErrorInfo.SERVER_ERROR_CODE);
        }
    }

    private void checkPasswords(String password, String repeatPassword, Map<String, String> errors) {
        if (password.equals(repeatPassword)) {
            if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
                errors.put(Const.UserField.PASSWORD, Const.ErrorInfo.WRONG_LENGTH);
            }
        } else {
            errors.put(Const.RequestParam.REPEAT_PASSWORD, Const.ErrorInfo.DIFFERENT_PASSWORDS);
        }
    }
}
