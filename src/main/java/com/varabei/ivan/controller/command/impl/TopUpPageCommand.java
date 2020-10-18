package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.Currency;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class TopUpPageCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(TopUpPageCommand.class);
    private static final String JSP_TOP_UP = "/WEB-INF/pages/topUp.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId = Long.parseLong(req.getParameter(Const.AccountField.ID));
        BigDecimal amount = new BigDecimal(req.getParameter(Const.BidField.AMOUNT));
        String currency = req.getParameter(Const.RequestParam.CURRENCY);
        try {
            BigDecimal amountInChosenCurrency = ServiceFactory.getInstance()
                    .getCurrencyService().convertUsdToAnotherCurrency(amount, Currency.valueOf(currency.toUpperCase()));
            req.setAttribute(Const.AttributeKey.AMOUNT_IN_CHOSEN_CURRENCY, amountInChosenCurrency);
            req.setAttribute(Const.BidField.ACCOUNT_ID, accountId);
            req.setAttribute(Const.BidField.AMOUNT, amount);
            req.getRequestDispatcher(JSP_TOP_UP).forward(req, resp);
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(Const.ErrorInfo.SERVER_ERROR_CODE);
        }

    }
}
