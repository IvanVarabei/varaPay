package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.common.ErrorInfo;
import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.name.UserField;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IncludeAccountsCommand implements ActionCommand {
    private static final Logger log = LogManager.getLogger(IncludeAccountsCommand.class);
    private static final AccountService accountService = ServiceFactory.getInstance().getAccountService();
    private static final String JSP_INCLUDE_ACCOUNTS = "/WEB-INF/pages/includeAccounts.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
//            req.setAttribute(AttributeKey.ACCOUNTS, accountService.findByUserId(Long.parseLong(req.getParameter(UserField.ID))));
            req.setAttribute(AttributeKey.ACCOUNTS, accountService.findByUserLogin(req.getParameter(UserField.LOGIN)));
            req.getRequestDispatcher(JSP_INCLUDE_ACCOUNTS).include(req, resp);
        } catch (ServiceException e) {
            log.error(e);
            resp.sendError(ErrorInfo.SERVER_ERROR_CODE);
        }
    }
}
