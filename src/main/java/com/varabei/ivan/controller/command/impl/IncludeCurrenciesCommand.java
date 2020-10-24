package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.Router;
import com.varabei.ivan.controller.RouterType;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.Currency;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class IncludeCurrenciesCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.INCLUDE_CURRENCIES, RouterType.INCLUDE);
        req.setAttribute(AttributeKey.CURRENCIES, Arrays.asList(Currency.values()));
        return router;
    }
}
