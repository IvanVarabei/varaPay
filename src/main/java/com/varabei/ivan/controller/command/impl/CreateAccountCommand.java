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

public class CreateAccountCommand implements ActionCommand {
    private AccountService accountService = ServiceFactory.getInstance().getAccountService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long userId= Long.parseLong(req.getParameter(Const.UserField.ID));
        try {
            accountService.create(userId);
            resp.sendRedirect(req.getContextPath() + "/mainServlet?command=profile_get");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
