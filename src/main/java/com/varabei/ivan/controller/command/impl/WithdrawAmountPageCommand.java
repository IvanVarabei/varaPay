package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.entity.CustomCurrency;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class WithdrawAmountPageCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(WithdrawAmountPageCommand.class);
    private static final AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
