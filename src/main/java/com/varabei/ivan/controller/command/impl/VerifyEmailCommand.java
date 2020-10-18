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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VerifyEmailCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(VerifyEmailCommand.class);
    private static final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final String JSP_VERIFY_EMAIL = "/WEB-INF/pages/verifyEmail.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String tempCode = req.getParameter(Const.RequestParam.TEMP_CODE);
        if (tempCode.equals(session.getAttribute(Const.RequestParam.TEMP_CODE).toString())) {
            try {
                User user = (User) session.getAttribute(Const.AttributeKey.USER);
                String login = user.getLogin();
                if (userService.findByLogin(login).isPresent()) {
                    req.setAttribute(Const.AttributeKey.ERROR, String.format(Const.ErrorInfo.LOGIN_TAKEN, login));
                    req.getRequestDispatcher(JSP_VERIFY_EMAIL).forward(req, resp);
                } else {
                    userService.signUp(user);
                    resp.sendRedirect(req.getContextPath());
                }
            } catch (ServiceException e) {
                log.error(e);
                resp.sendError(Const.ErrorInfo.SERVER_ERROR_CODE);
            }
        } else {
            req.setAttribute(Const.AttributeKey.ERROR, Const.ErrorInfo.WRONG_TEMP_CODE);
            req.getRequestDispatcher(JSP_VERIFY_EMAIL).forward(req, resp);
        }
    }
}
