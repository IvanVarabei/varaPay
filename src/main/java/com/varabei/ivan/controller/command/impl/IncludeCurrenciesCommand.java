package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.controller.router.RouterType;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.CustomCurrency;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class IncludeCurrenciesCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.INCLUDE_CURRENCIES, RouterType.INCLUDE);
        req.setAttribute(AttributeKey.CURRENCIES, Arrays.asList(CustomCurrency.values()));
        return router;
    }
}
