package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessPageCommand implements ActionCommand {
    private static final String JSP_PAYMENT_OVERVIEW = "/WEB-INF/pages/success.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(JSP_PAYMENT_OVERVIEW).forward(req, resp);
    }
}
