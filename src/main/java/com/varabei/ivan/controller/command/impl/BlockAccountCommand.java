package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.service.AccountService;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockAccountCommand implements ActionCommand {
    private static final AccountService accountService = ServiceFactory.getInstance().getAccountService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId= Long.parseLong(req.getParameter(Const.AccountField.ID));
        try {
            accountService.changeActive(accountId);
            resp.sendRedirect(req.getContextPath() + "/mainServlet?command=profile");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}