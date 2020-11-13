package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;
import com.epam.varapay.model.entity.Account;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.AccountService;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class IncludeAccountsCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(IncludeAccountsCommand.class);
    private static AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(JspPath.INCLUDE_ACCOUNTS, RouterType.INCLUDE);
        try {
            List<Account> accountList = accountService.findByUserLogin(req.getParameter(RequestParam.LOGIN));
            req.setAttribute(AttributeKey.ACCOUNTS, accountList);
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
