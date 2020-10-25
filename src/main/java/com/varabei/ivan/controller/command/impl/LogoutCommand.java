package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.controller.router.RouterType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        return new Router(req.getContextPath(), RouterType.REDIRECT);
    }
}