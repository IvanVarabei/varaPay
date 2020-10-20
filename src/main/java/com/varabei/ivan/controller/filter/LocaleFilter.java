package com.varabei.ivan.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class LocaleFilter implements Filter {

    private static final String LOCALE_ATTRIBUTE = "locale";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute(LOCALE_ATTRIBUTE) == null) {
            Locale locale = req.getLocale();
            session.setAttribute(LOCALE_ATTRIBUTE, locale);
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
