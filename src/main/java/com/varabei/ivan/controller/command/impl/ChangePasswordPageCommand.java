package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangePasswordPageCommand implements ActionCommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/WEB-INF/pages/changePassword.jsp").forward(req, resp);
    }
}