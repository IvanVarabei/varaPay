package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.CommandPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.model.service.UserService;
import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;
import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RecoverPasswordCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(RecoverPasswordCommand.class);
    private static UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(String.format(CommandPath.LOGIN, req.getContextPath()), RouterType.REDIRECT);
        String email = req.getParameter(RequestParam.EMAIL);
        try {
            if (!userService.recoverPassword(email)) {
                req.setAttribute(AttributeKey.ERROR, true);
                router.setForward(JspPath.RECOVER_PASSWORD);
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
