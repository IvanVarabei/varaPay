package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;
import com.varabei.ivan.controller.router.RouterType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class ChangeLanguageCommand implements ActionCommand {
    private static final String FORWARD_TO_PREVIOUS_PAGE = "/mainServlet?%s";
    private static final String REDIRECT = "%s/mainServlet";

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

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router(String.format(REDIRECT, req.getContextPath()), RouterType.REDIRECT);
        String localeParameter = req.getParameter(RequestParam.LOCALE);
        Locale.Builder builder = new Locale.Builder();
        builder.setLanguageTag(localeParameter);
        Locale locale = builder.build();
        HttpSession session = req.getSession();
        session.setAttribute(AttributeKey.LOCALE, locale);
        StringBuilder sb = new StringBuilder();
        if(req.getParameterValues(RequestParam.COMMAND).length > 1) {
            Map<String, String[]> paramMap = req.getParameterMap();
            for (Map.Entry<String, String[]> pair : paramMap.entrySet()) {
                if (!pair.getKey().equals("locale")) {//todo
                    String[] values = pair.getValue();
                    for (String value : values) {
                        if (!value.equals("change_language")) {
                            sb.append(pair.getKey());
                            sb.append("=");
                            sb.append(value);
                            sb.append("&");
                        }
                    }
                }
            }
            router.setForward(String.format(FORWARD_TO_PREVIOUS_PAGE, sb.toString()));
        }
        return router;
    }

}
