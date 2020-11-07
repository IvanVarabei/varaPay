package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.CommandPath;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;
import com.epam.varapay.model.entity.CustomCurrency;
import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.service.BidService;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class PlaceBidCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(PlaceBidCommand.class);
    private static BidService bidService = ServiceFactory.getInstance().getBidService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
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
