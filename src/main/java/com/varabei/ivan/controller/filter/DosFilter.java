package com.varabei.ivan.controller.filter;

import com.varabei.ivan.model.service.DosProtectionService;
import com.varabei.ivan.model.service.ServiceFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {
    private static DosProtectionService dosProtectionService = ServiceFactory.getInstance().getDosProtectionService();
    private static final int TOO_MANY_REQUESTS_CODE = 429;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        if (dosProtectionService.isAllowed(req.getRemoteAddr())) {
            filterChain.doFilter(req, resp);
        } else {
            ((HttpServletResponse) resp).setStatus(TOO_MANY_REQUESTS_CODE);
        }
    }

    @Override
    public void destroy() {
    }
}
