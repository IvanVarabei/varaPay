package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.Error;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.RedirectPath;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.controller.router.RouterType;
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
    private static UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(String.format(RedirectPath.PROFILE, req.getContextPath()), RouterType.REDIRECT);
        String login = req.getParameter(UserField.LOGIN);
        String password = req.getParameter(UserField.PASSWORD);
        HttpSession session = req.getSession();
        try {
            Optional<User> user = userService.signIn(login, password);
            if (user.isPresent()) {
                session.setAttribute(UserField.ROLE_NAME, user.get().getRole().name().toLowerCase());
                session.setAttribute(UserField.LOGIN, user.get().getLogin());
            } else {
                req.setAttribute(AttributeKey.ERROR, Error.LOGIN_OR_PASSWORD);
                router.setForward(JspPath.LOGIN);
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
