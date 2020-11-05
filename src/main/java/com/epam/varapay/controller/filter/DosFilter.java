package com.epam.varapay.controller.filter;

import com.epam.varapay.util.DosProtection;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {
    private static DosProtection dosProtection = new DosProtection();
    private static final int TOO_MANY_REQUESTS_CODE = 429;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        if (dosProtection.isAllowed(req.getRemoteAddr())) {
            filterChain.doFilter(req, resp);
        } else {
            ((HttpServletResponse) resp).setStatus(TOO_MANY_REQUESTS_CODE);
        }
    }

    @Override
    public void destroy() {
    }
}
