package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class TopUpPageCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId = Long.parseLong(req.getParameter(Const.AccountField.ID));
        BigDecimal amount = new BigDecimal(req.getParameter(Const.BidField.AMOUNT));
        req.setAttribute("account_id" , accountId);
        req.setAttribute("amount" , amount);
        req.getRequestDispatcher("/WEB-INF/pages/topUp.jsp").forward(req, resp);
    }
}
