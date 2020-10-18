package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.Currency;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TopUpAmountPageCommand implements ActionCommand {
    private static final String JPS_INPUT_TOP_UP_AMOUNT = "/WEB-INF/pages/inputTopUpAmount.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId = Long.parseLong(req.getParameter(Const.AccountField.ID));
        req.setAttribute(Const.AccountField.ID, accountId);
        req.setAttribute(Const.AttributeKey.CURRENCIES, List.of(Currency.values()));
        req.getRequestDispatcher(JPS_INPUT_TOP_UP_AMOUNT).forward(req, resp);
    }
}
