package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.model.entity.CustomCurrency;

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
