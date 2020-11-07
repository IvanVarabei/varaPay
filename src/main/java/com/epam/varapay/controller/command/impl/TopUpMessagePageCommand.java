package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.model.entity.CustomCurrency;
import com.epam.varapay.util.CurrencyConverter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TopUpMessagePageCommand implements ActionCommand {
    private static CurrencyConverter currencyConverter = CurrencyConverter.getInstance();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(JspPath.TOP_UP_MESSAGE);
        Map<String, String> dataToConvert = new HashMap<>();
        dataToConvert.put(RequestParam.ACCOUNT_ID, req.getParameter(RequestParam.ACCOUNT_ID));
        dataToConvert.put(RequestParam.AMOUNT, req.getParameter(RequestParam.AMOUNT));
        dataToConvert.put(RequestParam.CURRENCY, req.getParameter(RequestParam.CURRENCY));
        req.setAttribute(RequestParam.ACCOUNT_ID, Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID)));
        Optional<BigDecimal> amountInCurrency = currencyConverter.convertUsdToAnotherCurrency(dataToConvert);
        if (amountInCurrency.isPresent()) {
            req.setAttribute(AttributeKey.AMOUNT_IN_CHOSEN_CURRENCY, amountInCurrency.get());
            req.setAttribute(AttributeKey.AMOUNT, new BigDecimal(req.getParameter(RequestParam.AMOUNT)));
            req.setAttribute(AttributeKey.CURRENCY, CustomCurrency.valueOf(req.getParameter(RequestParam.CURRENCY)));
        } else {
            req.setAttribute(AttributeKey.ERRORS, dataToConvert);
            router.setForward(JspPath.TOP_UP_AMOUNT);
        }
        return router;
    }
}
