package com.varabei.ivan.controller.command.impl;

import com.varabei.ivan.controller.AttributeKey;
import com.varabei.ivan.controller.RequestParam;
import com.varabei.ivan.controller.command.ActionCommand;
import com.varabei.ivan.controller.router.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class ChangeLanguageCommand implements ActionCommand {
    private static final String REDIRECT_TO_PREVIOUS_PAGE = "%s/mainServlet?%s";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Router router = new Router();
        String localeParameter = req.getParameter(RequestParam.LOCALE);
        Locale.Builder builder = new Locale.Builder();
        builder.setLanguageTag(localeParameter);
        Locale locale = builder.build();
        HttpSession session = req.getSession();
        session.setAttribute(AttributeKey.LOCALE, locale);

        StringBuilder sb = new StringBuilder();
        Map<String, String[]> paramMap = req.getParameterMap();
        for (Map.Entry<String, String[]> pair : paramMap.entrySet()) {
            if (!pair.getKey().equals("locale")) {
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
        String previousQueryString = sb.toString();
        router.setRedirect(String.format(REDIRECT_TO_PREVIOUS_PAGE, req.getContextPath(), previousQueryString));
        return router;
    }
}
