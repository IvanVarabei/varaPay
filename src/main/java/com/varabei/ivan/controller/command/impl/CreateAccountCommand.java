package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RedirectPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.controller.router.RouterType;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateAccountCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(CreateAccountCommand.class);
    private static AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(String.format(RedirectPath.PROFILE, req.getContextPath()), RouterType.REDIRECT);
        Long userId = Long.parseLong(req.getParameter(RequestParam.USER_ID));
        try {
            accountService.create(userId);
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
