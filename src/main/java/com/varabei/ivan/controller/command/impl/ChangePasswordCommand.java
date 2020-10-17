package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.User;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.MailService;
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

public class ChangePasswordCommand implements ActionCommand {
    private static final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final Logger log = LogManager.getLogger(SignupCommand.class);
    private static final String JSP_CHANGE_PASSWORD = "/WEB-INF/pages/changePassword.jsp";
    private static final String FORWARD_VERIFY_EMAIL_PAGE_GET = "/WEB-INF/pages/verifyEmail.jsp";
    private static final int MIN_PASSWORD_LENGTH = 3;
    private static final int MAX_PASSWORD_LENGTH = 20;
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String oldPassword = req.getParameter("old_password");
        String password = req.getParameter(Const.UserField.PASSWORD);
        String repeatPassword = req.getParameter(Const.UserField.REPEAT_PASSWORD);
        Long userId = Long.parseLong(req.getSession().getAttribute(Const.UserField.ID).toString());
        Map<String, String> errors = new HashMap<>();
        try {
            checkPasswords(password, repeatPassword, errors);
            if(!userService.checkPresenceByIdPassword(userId, oldPassword)){
                errors.put("oldPassword", "wrong old password");
            }
            if (errors.isEmpty()) {
                userService.updatePassword(userId, password);
                resp.sendRedirect(req.getContextPath() + "/mainServlet?command=profile_get");
            } else {
                req.setAttribute(Const.AttributeKey.ERRORS, errors);
                req.getRequestDispatcher(JSP_CHANGE_PASSWORD).forward(req, resp);
            }
        } catch (ServiceException e) {
           // log.error(e);
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
}
