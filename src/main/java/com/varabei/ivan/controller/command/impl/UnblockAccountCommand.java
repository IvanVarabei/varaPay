package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.RedirectPath;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnblockAccountCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(UnblockAccountCommand.class);
    private static AccountService accountService = ServiceFactory.getInstance().getAccountService();
    private static UserService userService = ServiceFactory.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(JspPath.RUN_ACCOUNTS);
        String secretWord = req.getParameter(RequestParam.SECRET_WORD);
        Long accountId = Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID));
        String query = req.getParameter(RequestParam.QUERY);
        try {
            if (userService.isAuthenticSecretWord(accountId, secretWord)) {
                accountService.changeActive(accountId);
                router.setRedirect(String.format(RedirectPath.RUN_ACCOUNTS, req.getContextPath(), query));
            } else {
                req.setAttribute(AttributeKey.ACCOUNTS, accountService.findDisabledByLoginOrAccountId(query));
                req.setAttribute(AttributeKey.ERROR, ErrorInfo.SECRET_WORD.name().toLowerCase());
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
