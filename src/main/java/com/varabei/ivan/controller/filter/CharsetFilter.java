package com.varabei.ivan.controller.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {
    private static final String ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        req.setCharacterEncoding(ENCODING);
        resp.setCharacterEncoding(ENCODING);
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}