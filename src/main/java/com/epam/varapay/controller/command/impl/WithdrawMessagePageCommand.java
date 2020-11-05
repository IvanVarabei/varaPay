package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.model.entity.Account;
import com.epam.varapay.model.entity.CustomCurrency;
import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.service.AccountService;
import com.epam.varapay.model.service.BidService;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class WithdrawMessagePageCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(WithdrawMessagePageCommand.class);
    private static BidService bidService = ServiceFactory.getInstance().getBidService();
    private static final AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.WITHDRAW_MESSAGE);
        Map<String, String> dataToConvert = new HashMap<>();
        dataToConvert.put(RequestParam.ACCOUNT_ID, req.getParameter(RequestParam.ACCOUNT_ID));
        dataToConvert.put(RequestParam.AMOUNT, req.getParameter(RequestParam.AMOUNT));
        dataToConvert.put(RequestParam.CURRENCY, req.getParameter(RequestParam.CURRENCY));
        req.setAttribute(RequestParam.ACCOUNT_ID, Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID)));
        try {
            Optional<BigDecimal> amountInChosenCurrency =
                    bidService.findAmountInChosenCurrencyIfEnoughBalance(dataToConvert);
            if (amountInChosenCurrency.isPresent()) {
                req.setAttribute(AttributeKey.AMOUNT_IN_CHOSEN_CURRENCY, amountInChosenCurrency.get());
                req.setAttribute(AttributeKey.AMOUNT, new BigDecimal(req.getParameter(RequestParam.AMOUNT)));
                req.setAttribute(AttributeKey.CURRENCY, CustomCurrency.valueOf(req.getParameter(RequestParam.CURRENCY)));
            } else {
                Long accountId = Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID));
                Account account = accountService.findById(accountId).get();
                req.setAttribute(AttributeKey.ACCOUNT, account);
                req.setAttribute(AttributeKey.ERRORS, dataToConvert);
                router.setForward(JspPath.WITHDRAW_AMOUNT);
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
