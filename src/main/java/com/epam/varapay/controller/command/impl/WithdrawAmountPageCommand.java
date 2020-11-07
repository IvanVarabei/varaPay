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
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class WithdrawAmountPageCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(WithdrawAmountPageCommand.class);
    private static final AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(JspPath.WITHDRAW_AMOUNT);
        try {
            Account account = accountService.findById(Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID))).get();
            req.setAttribute(AttributeKey.ACCOUNT, account);
            req.setAttribute(AttributeKey.CURRENCIES, List.of(CustomCurrency.values()));
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
