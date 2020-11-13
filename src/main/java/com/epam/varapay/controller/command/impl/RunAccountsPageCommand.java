package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.AccountService;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

public class RunAccountsPageCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(RunAccountsPageCommand.class);
    private static AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(JspPath.RUN_ACCOUNTS);
        String query = req.getParameter(RequestParam.QUERY);
        try {
            if (query == null || query.isEmpty()) {
                req.setAttribute(AttributeKey.ACCOUNTS, Collections.emptyList());
            } else {
                req.setAttribute(AttributeKey.ACCOUNTS, accountService.findDisabledByLoginOrAccountId(query));
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
