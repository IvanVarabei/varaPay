package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.Const;
import com.varabei.ivan.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TopUpAmountPageCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Long accountId = Long.parseLong(req.getParameter(Const.AccountField.ID));
        req.setAttribute("account_id" , accountId);
        req.getRequestDispatcher("/WEB-INF/pages/inputTopUpAmount.jsp").forward(req, resp);
    }
}
