package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.CommandPath;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.JspPath;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.model.exception.ServiceException;
import com.epam.varapay.model.service.AccountService;
import com.epam.varapay.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockAccountCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(BlockAccountCommand.class);
    private static AccountService accountService = ServiceFactory.getInstance().getAccountService();

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router();
        Long accountId = Long.parseLong(req.getParameter(RequestParam.ACCOUNT_ID));
        try {
            accountService.changeActive(accountId);
            String page = req.getParameter(RequestParam.PAGE);
            router.setRedirect(String.format(CommandPath.ACCOUNT, req.getContextPath(), accountId, page));
        } catch (ServiceException e) {
            log.error(e);
            router.setForward(JspPath.ERROR_500);
        }
        return router;
    }
}
