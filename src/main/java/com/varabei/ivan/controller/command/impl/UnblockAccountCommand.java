package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.Router;
import com.varabei.ivan.controller.RouterType;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.command.RedirectPath;
import com.varabei.ivan.model.entity.name.AccountField;
import com.varabei.ivan.model.entity.name.UserField;
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
        Router router = new Router(String.format(RedirectPath.RUN_ACCOUNTS, req.getContextPath()), RouterType.REDIRECT);
        String secretWord = req.getParameter(UserField.SECRET_WORD);
        String login = req.getSession().getAttribute(UserField.LOGIN).toString();
        Long accountId = Long.parseLong(req.getParameter(AccountField.ID));
        try {
            if (userService.isAuthenticSecretWord(login, secretWord)) {
                accountService.changeActive(accountId);
            } else {
                router.setForward("");
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
