package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomeCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.WELCOME);
        try {
            req.setAttribute(AttributeKey.USER_AMOUNT, new UserServiceImpl().findAll().size());
            //req.setAttribute(AttributeKey.PAYMENT_AMOUNT, new PaymentServiceImpl().findAll().size());
        } catch (ServiceException e) {
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
