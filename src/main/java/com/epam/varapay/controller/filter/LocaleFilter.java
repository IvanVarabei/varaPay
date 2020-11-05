package com.epam.varapay.controller.filter;

import com.epam.varapay.controller.AttributeKey;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class LocaleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute(AttributeKey.LOCALE) == null) {
            Locale locale = req.getLocale();
            session.setAttribute(AttributeKey.LOCALE, locale);
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
