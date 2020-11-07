package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.CommandPath;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.service.AccountService;
import com.epam.varapay.model.service.ServiceFactory;
import com.epam.varapay.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EnableAccountCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(EnableAccountCommand.class);
    private static AccountService accountService = ServiceFactory.getInstance().getAccountService();
    private static UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(JspPath.RUN_ACCOUNTS);
        String secretWord = req.getParameter(RequestParam.SECRET_WORD);
        Long accountId = Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID));
        String query = req.getParameter(RequestParam.QUERY);
        try {
            if (userService.isAuthenticSecretWord(accountId, secretWord)) {
                accountService.changeActive(accountId);
                router.setRedirect(String.format(CommandPath.RUN_ACCOUNTS, req.getContextPath(), query));
            } else {
                req.setAttribute(AttributeKey.ACCOUNTS, accountService.findDisabledByLoginOrAccountId(query));
                req.setAttribute(AttributeKey.ERROR, true);
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
