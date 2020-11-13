package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class LogoutCommand implements ActionCommand {
    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        Locale locale = (Locale) session.getAttribute(AttributeKey.LOCALE);
        session.invalidate();
        session = req.getSession();
        session.setAttribute(AttributeKey.LOCALE, locale);
        return new Router(req.getContextPath(), RouterType.REDIRECT);
    }
}