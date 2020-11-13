package com.epam.varapay.controller.command.impl;

import com.epam.varapay.controller.AttributeKey;
import com.epam.varapay.controller.RequestParam;
import com.epam.varapay.controller.command.ActionCommand;
import com.epam.varapay.controller.command.CommandType;
import com.epam.varapay.controller.router.Router;
import com.epam.varapay.controller.router.RouterType;
import org.apache.logging.log4j.util.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Map;

public class ChangeLanguageCommand implements ActionCommand {
    private static final String SERVLET_ADDRESS = "%s/mainServlet?%s";
    private static final String EQUAL_SYMBOL = "=";
    private static final String AMPERSAND_SYMBOL = "&";

    @Override
    public Router execute(HttpServletRequest req, HttpServletResponse resp) {
        Router router = new Router(String.format(SERVLET_ADDRESS, req.getContextPath(), Strings.EMPTY),
                RouterType.REDIRECT);
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
                        if (!value.equalsIgnoreCase(CommandType.CHANGE_LANGUAGE_POST.name())) {
                            queryString.append(pair.getKey());
                            queryString.append(EQUAL_SYMBOL);
                            queryString.append(value);
                            queryString.append(AMPERSAND_SYMBOL);
                        }
                    }
                }
            }
            router.setForward(String.format(SERVLET_ADDRESS, Strings.EMPTY, queryString.toString()));
        }
        return router;
    }
}
