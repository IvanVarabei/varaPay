package com.varabei.ivan.controller.filter;

import com.varabei.ivan.controller.command.client.CommandType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionFilter implements Filter {
    Map<String, List<String>> commandNamePermittedRoles = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commandNamePermittedRoles.put(CommandType.PROFILE.name().toLowerCase(), List.of("админ", "клиент"));
        commandNamePermittedRoles.put("block_user_post", List.of("админ"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String command = req.getParameter("command");
        List<String> allowedRoles = commandNamePermittedRoles.get(command);
        Object userRole = session.getAttribute("role");
        if (userRole == null && allowedRoles != null) {
            resp.sendRedirect(req.getContextPath()+"/mainServlet?command=login_get");
        } else {
            if (allowedRoles == null || allowedRoles.contains(userRole)) {
                chain.doFilter(req, resp);
            } else {
                req.getRequestDispatcher(req.getContextPath()).forward(req, resp);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
