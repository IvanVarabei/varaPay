package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.entity.CustomCurrency;
import com.varabei.ivan.model.service.CurrencyService;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TopUpMessagePageCommand implements ActionCommand {
    private static CurrencyService currencyService = ServiceFactory.getInstance().getCurrencyService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.TOP_UP_MESSAGE);
        Map<String, String> dataToConvert = new HashMap<>();
        dataToConvert.put(RequestParam.ACCOUNT_ID, req.getParameter(RequestParam.ACCOUNT_ID));
        dataToConvert.put(RequestParam.AMOUNT, req.getParameter(RequestParam.AMOUNT));
        dataToConvert.put(RequestParam.CURRENCY, req.getParameter(RequestParam.CURRENCY));
        req.setAttribute(RequestParam.ACCOUNT_ID, Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID)));
        Optional<BigDecimal> amountInCurrency = currencyService.convertUsdToAnotherCurrency(dataToConvert);
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
