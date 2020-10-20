package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.command.ActionCommand;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class LoginCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(LoginCommand.class);
    private static final UserService userService = ServiceFactory.getInstance().getUserService();
    private static final String REDIRECT_AFTER_LOGIN = "%s/mainServlet?command=profile_get";
    private static final String JSP_LOGIN = "/WEB-INF/pages/login.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter(UserField.LOGIN);
        String password = req.getParameter(UserField.PASSWORD);
        HttpSession session = req.getSession();
        try {
            Optional<User> user = userService.signIn(login, password);
            if (user.isPresent()) {
                session.setAttribute(UserField.ROLE_NAME, user.get().getRoleName());
                session.setAttribute(UserField.ID, user.get().getId());
                resp.sendRedirect(String.format(REDIRECT_AFTER_LOGIN, req.getContextPath()));
            } else {
                req.setAttribute(AttributeKey.ERROR, ErrorInfo.WRONG_LOGIN_OR_PASSWORD);
                req.getRequestDispatcher(JSP_LOGIN).forward(req, resp);
            }
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
