package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.model.service.UserService;
import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProfileCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(ProfileCommand.class);
    private static UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(JspPath.PROFILE);
        HttpSession session = req.getSession();
        String login = session.getAttribute(RequestParam.LOGIN).toString();
        try {
            req.setAttribute(AttributeKey.USER, userService.findByLogin(login).orElse(null));
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
