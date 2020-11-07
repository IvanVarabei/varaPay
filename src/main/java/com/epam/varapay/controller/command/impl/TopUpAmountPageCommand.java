package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.model.entity.CustomCurrency;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TopUpAmountPageCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Long accountId = Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID));
        req.setAttribute(AttributeKey.ACCOUNT_ID, accountId);
        req.setAttribute(AttributeKey.CURRENCIES, List.of(CustomCurrency.values()));
        return new Router(JspPath.TOP_UP_AMOUNT);
    }
}
