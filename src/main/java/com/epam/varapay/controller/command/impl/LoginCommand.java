package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.CommandPath;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.entity.User;
import com.epam.varapay.model.service.ServiceFactory;
import com.epam.varapay.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(LoginCommand.class);
    private UserService userService = getUserService();

    /**
     * The method is necessary to prevent connection pool initialization during testing.
     * Testing class can have inner class which overrides this method.
     *
     * @return user service instance.
     */
    protected UserService getUserService() {
        return ServiceFactory.getInstance().getUserService();
    }

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(String.format(CommandPath.PROFILE, req.getContextPath()), RouterType.REDIRECT);
        String login = req.getParameter(RequestParam.LOGIN);
        String password = req.getParameter(RequestParam.PASSWORD);
        HttpSession session = req.getSession();
        try {
            Optional<User> user = userService.signIn(login, password);
            if (user.isPresent()) {
                session.setAttribute(AttributeKey.ROLE_NAME, user.get().getRole().name().toLowerCase());
                session.setAttribute(AttributeKey.LOGIN, login);
            } else {
                req.setAttribute(AttributeKey.ERROR, true);
                router.setForward(JspPath.LOGIN);
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
