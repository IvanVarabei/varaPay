package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.CommandPath;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;
import com.epam.varapay.exception.ServiceException;
import com.epam.varapay.model.service.AccountService;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class DeleteAccountCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(DeleteAccountCommand.class);
    private static AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(String.format(CommandPath.PROFILE, req.getContextPath()), RouterType.REDIRECT);
        Long accountId = Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID));
        try {
            Optional<String> error = accountService.delete(accountId);
            if(error.isPresent()){
                req.setAttribute(AttributeKey.ERROR, error.get());
                router.setForward(String.format(CommandPath.PROFILE, Strings.EMPTY));
            }
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
