package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.command.ActionCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class ChangeLanguageCommand implements ActionCommand {
    private static final String LOCALE_PARAMETER = "locale";
    private static final String LOCALE_ATTRIBUTE = "locale";
    private static final String REFERER = "referer";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String localeParameter = req.getParameter(LOCALE_PARAMETER);
        Locale.Builder builder = new Locale.Builder();
        builder.setLanguageTag(localeParameter);
        Locale locale = builder.build();
        HttpSession session = req.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, locale);
        String page = getCurrentPage(req);
        resp.sendRedirect(req.getContextPath() + page);
    }

    public static String getCurrentPage(HttpServletRequest request) {
        String header = request.getHeader(REFERER);
        String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath();
        return header.replace(serverUrl, "");
    }
}
