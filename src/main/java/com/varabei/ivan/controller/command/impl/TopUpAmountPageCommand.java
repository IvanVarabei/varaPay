package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.entity.Currency;
import com.varabei.ivan.model.entity.name.AccountField;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TopUpAmountPageCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId = Long.parseLong(req.getParameter(AccountField.ID));
        req.setAttribute(AccountField.ID, accountId);
        req.setAttribute(AttributeKey.CURRENCIES, List.of(Currency.values()));
        return new Router(JspPath.INPUT_TOP_UP_AMOUNT);
    }
}
