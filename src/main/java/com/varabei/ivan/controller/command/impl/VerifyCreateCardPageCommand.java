package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.JspPath;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VerifyCreateCardPageCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        return new Router(JspPath.VERIFY_CREATE_CARD);
    }
}
