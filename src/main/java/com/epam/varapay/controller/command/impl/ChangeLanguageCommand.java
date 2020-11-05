package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.command.CommandType;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class ChangeLanguageCommand implements ActionCommand {
    private static final String SERVLET_ADDRESS = "%s/mainServlet?%s";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(String.format(SERVLET_ADDRESS, req.getContextPath(), ""), RouterType.REDIRECT);
        String localeParameter = req.getParameter(RequestParam.LOCALE);
        Locale.Builder builder = new Locale.Builder();
        builder.setLanguageTag(localeParameter);
        Locale locale = builder.build();
        HttpSession session = req.getSession();
        session.setAttribute(AttributeKey.LOCALE, locale);
        StringBuilder queryString = new StringBuilder();
        if (req.getParameterValues(RequestParam.COMMAND).length > 1) {
            Map<String, String[]> paramMap = req.getParameterMap();
            for (Map.Entry<String, String[]> pair : paramMap.entrySet()) {
                if (!pair.getKey().equals(RequestParam.LOCALE)) {
                    String[] values = pair.getValue();
                    for (String value : values) {
                        if (!value.equalsIgnoreCase(CommandType.CHANGE_LANGUAGE.name())) {
                            queryString.append(pair.getKey());
                            queryString.append("=");
                            queryString.append(value);
                            queryString.append("&");
                        }
                    }
                }
            }
            router.setForward(String.format(SERVLET_ADDRESS, "", queryString.toString()));
        }
        return router;
    }

//    @Override
//    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//        Router router = new Router();
//        String localeParameter = req.getParameter("locale");
//        Locale.Builder builder = new Locale.Builder();
//        builder.setLanguageTag(localeParameter);
//        Locale locale = builder.build();
//        HttpSession session = req.getSession();
//        session.setAttribute("locale", locale);
//        String page = req.getContextPath()  +getCurrentPage(req);
//        router.setRedirect(page);
//        return router;
//    }
//    public static String getCurrentPage(HttpServletRequest request) {
//        String header = request.getHeader("referer");
//        String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
//                request.getContextPath();
//        return header.replace(serverUrl, "");
//    }
}
