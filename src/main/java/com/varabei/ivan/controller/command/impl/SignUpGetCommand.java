package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpGetCommand implements ActionCommand {
    private static final String JSP_REGISTER = "/WEB-INF/pages/signup.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(JSP_REGISTER).forward(req, resp);
    }
}
