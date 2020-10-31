package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.entity.CustomCurrency;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TopUpAmountPageCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId = Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID));
        req.setAttribute(RequestParam.ACCOUNT_ID, accountId);
        req.setAttribute(AttributeKey.CURRENCIES, List.of(CustomCurrency.values()));
        return new Router(JspPath.INPUT_TOP_UP_AMOUNT);
    }
}
