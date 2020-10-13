package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.model.entity.Account;
import com.varabei.ivan.model.exception.ServiceException;
import com.varabei.ivan.model.service.ServiceFactory;
import com.varabei.ivan.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class IncludeAccountsGet implements ActionCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            req.setAttribute("accounts",
                    userService.findById(Long.parseLong(req.getParameter("user_id"))).get().getAccounts());
            req.getRequestDispatcher("/WEB-INF/pages/account.jsp").include(req, resp);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
