package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.entity.Currency;
import com.varabei.ivan.model.entity.name.AccountField;
import com.varabei.ivan.model.entity.name.BidField;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class TopUpPageCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.TOP_UP);
        Long accountId = Long.parseLong(req.getParameter(AccountField.ID));
        BigDecimal amount = new BigDecimal(req.getParameter(BidField.AMOUNT));
        String currency = req.getParameter(RequestParam.CURRENCY);
        BigDecimal amountInChosenCurrency = ServiceFactory.getInstance()
                .getCurrencyService().convertUsdToAnotherCurrency(amount, Currency.valueOf(currency.toUpperCase()));
        req.setAttribute(AttributeKey.AMOUNT_IN_CHOSEN_CURRENCY, amountInChosenCurrency);
        req.setAttribute(BidField.ACCOUNT_ID, accountId);
        req.setAttribute(BidField.AMOUNT, amount);
        return router;
    }
}
