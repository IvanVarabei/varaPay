package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RunAccountsPageCommand implements ActionCommand {
    private static final String JSP_ACCOUNTS = "/WEB-INF/pages/runAccounts.jsp";
    private static final AccountService accountService = ServiceFactory.getInstance().getAccountService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            req.setAttribute("accounts" , accountService.findDisabled());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher(JSP_ACCOUNTS).forward(req, resp);
    }
}
