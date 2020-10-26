package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.Error;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.entity.Currency;
import com.varabei.ivan.model.entity.name.AccountField;
import com.varabei.ivan.model.entity.name.BidField;
import com.varabei.ivan.model.service.CurrencyService;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TopUpPageCommand implements ActionCommand {
    private static CurrencyService currencyService = ServiceFactory.getInstance().getCurrencyService();
    private static final String DIGITS = "^\\d+\\.?\\d*$";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.TOP_UP);
        Long accountId = Long.parseLong(req.getParameter(AccountField.ID));
        String amount = req.getParameter(BidField.AMOUNT);
        String currency = req.getParameter(RequestParam.CURRENCY);
        Map<String, String> errors = new HashMap<>();
        req.setAttribute(BidField.ACCOUNT_ID, accountId);
        if (!amount.matches(DIGITS)) {
            errors.put(BidField.AMOUNT, Error.NOT_NUMBER.name().toLowerCase());
        }
        if (Arrays.stream(Currency.values()).noneMatch(c -> currency.equalsIgnoreCase(c.name()))) {
            errors.put("currency", Error.CAN_NOT_BE_EMPTY.name().toLowerCase());
        }
        if (errors.isEmpty()) {
            BigDecimal properAmount = new BigDecimal(amount);
            BigDecimal amountInChosenCurrency = currencyService
                    .convertUsdToAnotherCurrency(properAmount, Currency.valueOf(currency.toUpperCase()));
            req.setAttribute(AttributeKey.AMOUNT_IN_CHOSEN_CURRENCY, amountInChosenCurrency);
            req.setAttribute(BidField.AMOUNT, properAmount);
        } else {
            req.setAttribute(AttributeKey.ERRORS, errors);
            router.setForward(JspPath.INPUT_TOP_UP_AMOUNT);
        }
        return router;
    }
}
