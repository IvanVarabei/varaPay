package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterCommand implements ActionCommand {
    private static final String JSP_REGISTER = "/WEB-INF/pages/register.jsp";
    private static final String GET_METHOD = "GET";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getMethod().equals(GET_METHOD)) {
            doGet(req, resp);
        } else {
            doPost(req, resp);
        }
    }

    private void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(JSP_REGISTER).forward(req, resp);
    }

    private void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

    }
}
