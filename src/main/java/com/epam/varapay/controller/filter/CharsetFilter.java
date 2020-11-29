package com.epam.varapay.controller.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {
    private static final String INIT_CONTEXT_PARAM_NAME_ENCODING = "encoding";
    private String encoding;

    @Override
    public void init(FilterConfig config) {
        encoding = config.getInitParameter(INIT_CONTEXT_PARAM_NAME_ENCODING);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        req.setCharacterEncoding(encoding);
        resp.setCharacterEncoding(encoding);
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}