package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IncludeAccountsGet implements ActionCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        AccountService accountService = ServiceFactory.getInstance().getAccountService();
        try {
            req.setAttribute("accounts", accountService.findByUserId(Long.parseLong(req.getParameter(Const.UserField.ID))));
            req.getRequestDispatcher("/WEB-INF/pages/includeAccounts.jsp").include(req, resp);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
