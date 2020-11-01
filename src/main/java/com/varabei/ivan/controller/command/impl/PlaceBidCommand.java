package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.CommandPath;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.controller.router.RouterType;
import com.varabei.ivan.model.entity.CustomCurrency;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.BidService;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class PlaceBidCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(PlaceBidCommand.class);
    private static BidService bidService = ServiceFactory.getInstance().getBidService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(String.format(CommandPath.SUCCESS_PAGE, req.getContextPath()), RouterType.REDIRECT);
        Long accountId = Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID));
        BigDecimal amountUsd = new BigDecimal(req.getParameter(RequestParam.AMOUNT));
        BigDecimal amountInChosenCurrency = new BigDecimal(req.getParameter(RequestParam.AMOUNT_IN_CHOSEN_CURRENCY));
        CustomCurrency currency = CustomCurrency.valueOf(req.getParameter(RequestParam.CURRENCY));
        String message = req.getParameter(RequestParam.CLIENT_MESSAGE);
        try {
            if (Boolean.parseBoolean(req.getParameter(RequestParam.IS_TOP_UP))) {
                if (!bidService.placeTopUpBid(accountId, amountUsd, amountInChosenCurrency, currency, message)) {
                    req.setAttribute(AttributeKey.ERROR, true);
                    router.setForward(CommandPath.TOP_UP_MESSAGE_PAGE);
                }
            } else {
                if (!bidService.placeWithdrawBid(accountId, amountUsd, amountInChosenCurrency, currency, message)) {
                    req.setAttribute(AttributeKey.ERROR, true);
                    router.setForward(CommandPath.WITHDRAW_MESSAGE_PAGE);
                }
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
